package perobobbot.twitch.oauth.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.*;
import perobobbot.oauth.*;
import perobobbot.twitch.oauth.api.TwitchScope;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class TwitchOAuthController implements OAuthController {

    public static final ImmutableSet<TwitchScope> DEFAULT_SCOPES = ImmutableSet.of(
            TwitchScope.CHANNEL_MANAGE_REDEMPTIONS,
            TwitchScope.CHANNEL_MANAGE_POLLS,
            TwitchScope.CHANNEL_MANAGE_PREDICTIONS,
            TwitchScope.CHAT_READ,
            TwitchScope.CHAT_EDIT,

            TwitchScope.BITS_READ,
            TwitchScope.CHANNEL_READ_REDEMPTIONS,
            TwitchScope.USER_READ_BROADCAST,
            TwitchScope.CHANNEL_READ_SUBSCRIPTIONS,
            TwitchScope.USER_READ_FOLLOWS
    );

    private static final String TWITCH_OAUTH_PATH = "/twitch/oauth";

    private final @NonNull OAuthSubscriptions oAuthSubscriptions;
    private final @NonNull WebClient webClient;
    private final @NonNull Instants instants;

    private final @NonNull TwitchOAuthURI twitchOAuthURI = new TwitchOAuthURI();

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        final var revokeUri = twitchOAuthURI.getRevokeURI(client.getClientId(), accessToken);
        return MonoTools.toCompletionStageAsync(
                webClient.post()
                         .uri(revokeUri.getUri())
                         .retrieve()
                         .toBodilessEntity()
                         .map(ResponseEntity::getStatusCode)
        );
    }

    @Override
    public @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret tokenToRefresh) {
        final var refreshUri = twitchOAuthURI.getRefreshURI(client, tokenToRefresh);

        return MonoTools.toCompletionStageAsync(
                webClient.post()
                         .uri(refreshUri.getUri())
                         .retrieve()
                         .bodyToMono(TwitchRefreshedToken.class)
                         .map(TwitchRefreshedToken::toRefreshedToken)
        );
    }

    @Override
    public @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client) {
        final var tokenUri = twitchOAuthURI.getAppTokenURI(client);

        return MonoTools.toCompletionStageAsync(
                webClient.post()
                         .uri(tokenUri.getUri())
                         .retrieve()
                         .bodyToMono(TwitchToken.class)
                         .map(r -> r.toToken(instants.now()))
        );
    }

    @Override
    public @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options) {
        final var listener = new OAuthAuthorizationListener(webClient, client, instants);

        final var subscriptionData = oAuthSubscriptions.subscribe(TWITCH_OAUTH_PATH, listener);
        final var oauthURI = twitchOAuthURI.getUserAuthorizationURI(
                client.getClientId(),
                DEFAULT_SCOPES,
                subscriptionData.getState(),
                subscriptionData.getOAuthRedirectURI(),
                options);

        return new UserOAuthInfo<>(oauthURI, listener.getFutureToken());
    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken) {
        return performTokenValidation(accessToken);
    }

    @Override
    public @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Secret accessToken) {
        return performTokenValidation(accessToken).thenApply(TwitchValidation::toUserIdentity);
    }

    private @NonNull CompletionStage<TwitchValidation> performTokenValidation(@NonNull Secret accessToken) {
        final var validateUri = twitchOAuthURI.getValidateTokenURI();

        return MonoTools.toCompletionStageAsync(webClient.get()
                                                         .uri(validateUri)
                                                         .header(HttpHeaders.AUTHORIZATION,
                                                                 "OAuth " + accessToken.getValue())
                                                         .accept(MediaType.APPLICATION_JSON)
                                                         .retrieve()
                                                         .bodyToMono(TwitchValidation.class));

    }


}
