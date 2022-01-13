package perobobbot.twitch.oauth.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Instants;
import perobobbot.lang.MonoTools;
import perobobbot.lang.ThrowableTool;
import perobobbot.oauth.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class OAuthAuthorizationListener implements OAuthListener {

    public static final String CODE_PARAMETER_NAME = "code";

    private final @NonNull WebClient webClient;
    private final @NonNull DecryptedClient client;
    private final @NonNull Instants instants;

    @Getter
    private final @NonNull CompletableFuture<Token> futureToken = new CompletableFuture<>();

    @Override
    public void onCall(@NonNull URI redirectURI, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        try {

            formResponse(response);

            final var code = request.getParameter(CODE_PARAMETER_NAME);

            if (code == null) {
                futureToken.completeExceptionally(new OAuthRejected(client.getPlatform(), client.getClientId()));
            } else {
                final var secretURI = new TwitchOAuthURI().getUserTokenURI(client, code, redirectURI);

                MonoTools.setToCompletableFutureAsync(
                        webClient.post()
                                 .uri(secretURI.getUri())
                                 .retrieve()
                                 .bodyToMono(String.class)
                                 .map(this::toTwitchToken)
//                                 .bodyToMono(TwitchToken.class)
                                 .map(r -> r.toToken(instants.now()))
                                 .onErrorMap(e -> new OAuthFailure(client.getPlatform(), client.getClientId(), e))
                        ,
                        futureToken);
            }
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            futureToken.completeExceptionally(new OAuthFailure(client.getPlatform(), client.getClientId(), t));
        }
    }

    private TwitchToken toTwitchToken(@NonNull String body) {
        try {
            return new ObjectMapper().readValue(body, TwitchToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void formResponse(HttpServletResponse response) {

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setStatus(HttpStatus.OK.value());

        final var url = OAuthAuthorizationListener.class.getResource("oauth_response.html");
        if (url != null) {
            try (var b = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                final var out = response.getWriter();
                b.lines().forEach(out::println);
                out.flush();
                out.close();
            } catch (IOException e) {
                //ignored
            }
        }

    }

    @Override
    public void onTimeout() {
        futureToken.completeExceptionally(new OAuthTimedOut(client.getPlatform(), client.getClientId()));
    }

    @Override
    public void onInterrupted() {
        futureToken.completeExceptionally(new OAuthInterrupted(client.getPlatform(), client.getClientId()));
    }
}
