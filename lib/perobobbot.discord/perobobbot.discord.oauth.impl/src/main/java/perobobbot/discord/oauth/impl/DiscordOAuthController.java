package perobobbot.discord.oauth.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.discord.oauth.api.DiscordScope;
import perobobbot.discord.resources.AuthorizationInformation;
import perobobbot.discord.resources.DiscordRefreshedToken;
import perobobbot.discord.resources.DiscordToken;
import perobobbot.discord.resources.DiscordUser;
import perobobbot.lang.*;
import perobobbot.oauth.*;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class DiscordOAuthController implements OAuthController {

    private static final String DISCORD_OAUTH_PATH = "/discord/oauth";


    private static final ImmutableSet<DiscordScope> DEFAULT_SCOPES = ImmutableSet.of(DiscordScope.IDENTIFY);

    private final @NonNull OAuthSubscriptions oAuthSubscriptions;
    private final @NonNull WebClient webClient;
    private final @NonNull Instants instants;

    private final DiscordOAuthURI discordOAuthURI = new DiscordOAuthURI(8);

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.DISCORD;
    }

    @Override
    public @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client) {
        final var tokenRequest = discordOAuthURI.getAppTokenURI(client);

        return MonoTools.toCompletionStageAsync(
                tokenRequest.postWith(webClient)
                            .bodyToMono(DiscordToken.class)
                            .map(r -> r.toToken(instants.now()))
        );
    }

    @Override
    public @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        final var revokeUri = discordOAuthURI.getRevokeURI(client.getClientId(), accessToken);

        return MonoTools.toCompletionStageAsync(
                webClient.post()
                         .uri(revokeUri.getUri())
                         .retrieve()
                         .toBodilessEntity()
                         .map(ResponseEntity::getStatusCode)
        );
    }


    @Override
    public @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options) {
        final var listener = new OAuthAuthorizationListener(discordOAuthURI, webClient, client, instants);

        final var subscriptionData = oAuthSubscriptions.subscribe(DISCORD_OAUTH_PATH, listener);
        final var oauthURI = discordOAuthURI.getUserAuthorizationURI(
                client.getClientId(),
                DEFAULT_SCOPES,
                subscriptionData.getState(),
                subscriptionData.getOAuthRedirectURI(),
                options);

        return new UserOAuthInfo<>(oauthURI, listener.getFutureToken());
    }


    @Override
    public @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret tokenToRefresh) {
        final var tokenRequest = discordOAuthURI.getRefreshURI(client, tokenToRefresh);
        return MonoTools.toCompletionStageAsync(
                tokenRequest.postWith(webClient)
                            .bodyToMono(DiscordRefreshedToken.class)
                            .map(DiscordRefreshedToken::toRefreshedToken));

    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken) {
        return getUserIdentity(accessToken);
    }

    @Override
    public @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Secret accessToken) {
        final var discordRequest = discordOAuthURI.getUserInfoURI(accessToken);
        return MonoTools.toCompletionStageAsync(webClient.get()
                                                         .uri(discordRequest.getUri())
                                                         .headers(discordRequest::setupHeaders)
                                                         .retrieve()
                                                         .bodyToMono(AuthorizationInformation.class)
                                                         .map(AuthorizationInformation::getUser)
                                                         .map(DiscordUser::toUserIdentity));
    }

}
