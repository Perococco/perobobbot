package perobbot.twitch.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Client;
import perobobbot.lang.Instants;
import perobobbot.lang.ThrowableTool;
import perobobbot.oauth.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class OAuthAuthorizationListener implements OAuthListener {

    public static final String CODE_PARAMETER_NAME = "code";

    private final @NonNull WebClient webClient;
    private final @NonNull Client client;
    private final @NonNull Instants instants;

    @Getter
    private final @NonNull CompletableFuture<Token> futureToken = new CompletableFuture<>();

    @Override
    public void onCall(@NonNull URI redirectURI, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        try {

            response.setStatus(HttpStatus.OK.value());
            final var code = request.getParameter(CODE_PARAMETER_NAME);

            if (code == null) {
                futureToken.completeExceptionally(new OAuthRejected(client.getPlatform(), client.getClientId()));
            } else {
                final var secretURI = new TwitchOAuthURI().getUserTokenURI(client, code, redirectURI);

                webClient.post()
                         .uri(secretURI.getUri())
                         .retrieve()
                         .bodyToMono(TwitchToken.class)
                         .subscribe(result -> futureToken.complete(result.toToken(instants.now())),
                                    error -> futureToken.completeExceptionally(new OAuthFailure(client.getPlatform(), client.getClientId(), error)));
            }
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            futureToken.completeExceptionally(new OAuthFailure(client.getPlatform(), client.getClientId(), t));
        }
    }

    @Override
    public void onTimeout() {
        futureToken.completeExceptionally(new OAuthTimedOut(client.getPlatform(), client.getClientId()));
    }

}