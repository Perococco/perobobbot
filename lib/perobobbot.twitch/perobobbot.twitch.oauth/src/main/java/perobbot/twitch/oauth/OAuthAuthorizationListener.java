package perobbot.twitch.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestOperations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.lang.Instants;
import perobobbot.lang.Secret;
import perobobbot.lang.ThrowableTool;
import perobobbot.oauth.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class OAuthAuthorizationListener implements OAuthListener {

    public static final String CODE_PARAMETER_NAME = "code";

    private final @NonNull WebClient webClient;
    private final @NonNull ClientProperty clientProperty;
    private final @NonNull Instants instants;

    @Getter
    private final @NonNull CompletableFuture<Token> futureToken = new CompletableFuture<>();

    @Override
    public void onCall(@NonNull URI redirectURI, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        try {

            response.setStatus(HttpStatus.OK.value());
            final var code = request.getParameter(CODE_PARAMETER_NAME);

            if (code == null) {
                futureToken.completeExceptionally(new OAuthRejected(clientProperty.getId()));
            } else {
                final var secretURI = new TwitchOAuthURI().getUserTokenURI(clientProperty, code, redirectURI);

                webClient.post()
                         .uri(secretURI.getUri())
                         .retrieve()
                         .bodyToMono(TwitchToken.class)
                         .subscribe(result -> futureToken.complete(result.toToken(instants.now())),
                                    error -> futureToken.completeExceptionally(new OAuthFailure(clientProperty.getId(), error)));
            }
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            futureToken.completeExceptionally(new OAuthFailure(clientProperty.getId(), t));
        }
    }

    @Override
    public void onTimeout() {
        futureToken.completeExceptionally(new OAuthTimedOut(clientProperty.getId()));
    }

}
