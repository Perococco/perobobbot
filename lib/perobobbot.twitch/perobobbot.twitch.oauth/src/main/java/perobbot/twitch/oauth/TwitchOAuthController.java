package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.*;
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
    public @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        final var revokeUri = new TwitchOAuthURI().getRevokeURI(client.getClientId(), accessToken);
        return toCompletionStage(
                webClient.post()
                         .uri(revokeUri.getUri())
                         .retrieve()
                         .toBodilessEntity(), ResponseEntity::getStatusCode);
    }

    public @NonNull CompletionStage<Token> refreshToken(@NonNull DecryptedClient client, @NonNull Token expiredToken) {
        final var refreshToken = expiredToken.getRefreshToken().orElseThrow(
                () -> new OAuthNotRefreshableToken(client.getPlatform(), client.getClientId()));


        final var refreshUri = new TwitchOAuthURI().getRefreshURI(client, refreshToken);
        return toCompletionStage(
                webClient.post().uri(refreshUri.getUri()).retrieve().bodyToMono(TwitchRefreshedToken.class),
                r -> r.update(expiredToken)
        );
    }

    @Override
    public @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client) {
        final var tokenUri = new TwitchOAuthURI().getAppTokenURI(client);

        return toCompletionStage(webClient.post()
                                          .uri(tokenUri.getUri())
                                          .retrieve()
                                          .bodyToMono(TwitchToken.class),
                                 a -> a.toToken(instants.now()));
    }

    @Override
    public @NonNull UserOAuthInfo prepareUserOAuth(@NonNull DecryptedClient client, @NonNull ImmutableSet<? extends Scope> scopes) {
        final var listener = new OAuthAuthorizationListener(webClient, client, instants);

        final var subscriptionData = oAuthSubscriptions.subscribe(TWITCH_OAUTH_PATH, listener);
        final var oauthURI = new TwitchOAuthURI().getUserAuthorizationURI(client.getClientId(), scopes,
                                                                          subscriptionData.getState(),
                                                                          subscriptionData.getOAuthRedirectURI());

        return new UserOAuthInfo(oauthURI, listener.getFutureToken());
    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken) {
        return performTokenValidation(accessToken);
    }

    @Override
    public @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        return performTokenValidation(accessToken).thenApply(TwitchValidation::toUserIdentity);
    }

    private @NonNull CompletionStage<TwitchValidation> performTokenValidation(@NonNull Secret accessToken) {
        final var validateUri = new TwitchOAuthURI().getValidateTokenURI();
        return toCompletionStage(webClient
                .get()
                .uri(validateUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(TwitchValidation.class));
    }

    //TODO should be moved outside of Twitch module
    private <T, U> CompletionStage<T> toCompletionStage(@NonNull Mono<U> mono, Function1<? super U, ? extends T> mapper) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        mono.subscribe(
                u -> {
                    try {
                        var t = mapper.apply(u);
                        completableFuture.complete(t);
                    } catch (Throwable t) {
                        completableFuture.completeExceptionally(t);
                    }
                }, completableFuture::completeExceptionally);
        return completableFuture;
    }

    private <T> CompletionStage<T> toCompletionStage(@NonNull Mono<T> mono) {
        return toCompletionStage(mono, t -> t);
    }

}
