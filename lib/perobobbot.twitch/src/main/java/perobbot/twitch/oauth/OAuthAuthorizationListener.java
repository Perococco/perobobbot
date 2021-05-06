package perobbot.twitch.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.lang.Secret;
import perobobbot.lang.ThrowableTool;
import perobobbot.oauth.OAuthFailure;
import perobobbot.oauth.OAuthListener;
import perobobbot.oauth.OAuthTimedOut;
import perobobbot.oauth.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class OAuthAuthorizationListener implements OAuthListener {

    public static final String CODE_PARAMETER_NAME = "code";

    private final @NonNull RestOperations restOperations;
    private final @NonNull String clientId;
    private final @NonNull Secret clientSecret;

    @Getter
    private final @NonNull CompletableFuture<Token> futureToken = new CompletableFuture<>();

    @Override
    public void onCall(@NonNull URI redirectURI, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        try {
            response.setStatus(HttpStatus.OK.value());
            final var code = request.getParameter(CODE_PARAMETER_NAME);

            final var uri = UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/token")
                                                .queryParam("client_id", clientId)
                                                .queryParam("client_secret", clientSecret.getValue())
                                                .queryParam("code", code)
                                                .queryParam("grant_type", "authorization_code")
                                                .queryParam("redirect_uri", redirectURI)
                                                .build()
                                                .toUri();


            final var token = restOperations.postForObject(uri, null, Token.class);

            if (token == null) {
                futureToken.completeExceptionally(new OAuthFailure(clientId,"Received null token"));
            } else {
                futureToken.complete(token);
            }
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            futureToken.completeExceptionally(new OAuthFailure(clientId, t));
        }
    }

    @Override
    public void onTimeout() {
        futureToken.completeExceptionally(new OAuthTimedOut(clientId));
    }

}
