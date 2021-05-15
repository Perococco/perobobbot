package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Instants;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.fp.Function1;
import perobobbot.oauth.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class TwitchOAuthController implements OAuthController {

    public static final String TWITCH_OAUTH_PATH = "/twitch/oauth";

    private final @NonNull OAuthSubscriptions oAuthSubscriptions;
    private final @NonNull WebClient webClient;
    private final @NonNull Instants instants;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull CompletionStage<?> revokeToken(@NonNull String clientId, @NonNull String accessToken) {
        final var revokeUri = new TwitchOAuthURI().getRevokeURI(clientId, accessToken);
        return toCompletionStage(
                webClient.post()
                         .uri(revokeUri.getUri())
                         .retrieve()
                         .bodyToMono(String.class));
    }

    public @NonNull CompletionStage<Token> refreshToken(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull Token expiredToken) {
        final var refreshToken = expiredToken.getRefreshToken().orElseThrow(
                () -> new OAuthNotRefreshableToken(clientId));


        final var refreshUri = new TwitchOAuthURI().getRefreshURI(clientId, clientSecret, refreshToken);
        return toCompletionStage(
                webClient.post().uri(refreshUri.getUri()).retrieve().bodyToMono(TwitchRefreshedToken.class),
                r -> r.update(expiredToken)
        );
    }

    @Override
    public @NonNull CompletionStage<Token> getAppToken(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes) {
        final var tokenUri = new TwitchOAuthURI().getAppTokenURI(clientId, clientSecret, scopes);

        return toCompletionStage(webClient.post()
                                          .uri(tokenUri.getUri())
                                          .retrieve()
                                          .bodyToMono(TwitchToken.class),
                                 a -> a.toToken(instants.now()));
    }

    @Override
    public @NonNull UserOAuthInfo prepareUserOAuth(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes) {
        final var listener = new OAuthAuthorizationListener(webClient, clientId, clientSecret, instants);

        final var subscriptionData = oAuthSubscriptions.subscribe(TWITCH_OAUTH_PATH, listener);
        final var oauthURI = new TwitchOAuthURI().getUserAuthorizationURI(clientId, scopes, subscriptionData.getState(),
                                                                          subscriptionData.getOAuthRedirectURI());

        return new UserOAuthInfo(oauthURI, listener.getFutureToken());
    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Token token) {
        final var validateUri = new TwitchOAuthURI().getValidateTokenURI();
        return toCompletionStage(webClient
                                         .get()
                                         .uri(validateUri)
                                         .header("Authorization", "Bearer " + token.getAccessToken())
                                         .retrieve()
                                         .bodyToMono(TwitchValidation.class));
    }


    //TODO should be moved outside of Twitch module
    private <T, U> CompletionStage<T> toCompletionStage(@NonNull Mono<U> mono, Function1<? super U, ? extends T> mapper) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        mono.subscribe(result -> completableFuture.complete(mapper.apply(result)),
                       completableFuture::completeExceptionally);
        return completableFuture;
    }

    private <T> CompletionStage<T> toCompletionStage(@NonNull Mono<T> mono) {
        return toCompletionStage(mono, t -> t);
    }

}
