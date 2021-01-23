package perococco.perobobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import perobobbot.http.WebHookObservable;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Todo;
import perobobbot.twitch.oauth.AppAccessToken;
import perobobbot.twitch.oauth.ClientInfo;
import perobobbot.twitch.oauth.OAuthManager;
import perobobbot.twitch.oauth.Scope;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PerococcoOAuthManager implements OAuthManager {

    public static final String CLIENT_REQUEST =
            "https://id.twitch.tv/oauth2/token" +
                    "?client_id={clientId}" +
                    "&client_secret={clientSecret}" +
                    "&grant_type=client_credentials" +
                    "&scope={scope}";

    private final @NonNull ClientInfo clientInfo;

    private final @NonNull WebHookObservable webHookObservable;

    private final @NonNull RestTemplate restTemplate = new RestTemplate();

    @Override
    public @NonNull AppAccessToken getAppAccessToken(@NonNull ImmutableSet<Scope> scopes) {
        final var now = Instant.now();
        final var clientId = clientInfo.getId();
        final var clientSecret = clientInfo.getSecret();
        final var scope = scopes.stream().map(Scope::getId).collect(Collectors.joining(" "));

        final var token = restTemplate.postForObject(
                CLIENT_REQUEST,
                null,
                JsonClientCredential.class,
                Map.of("clientId", clientId,
                       "clientSecret", clientSecret.getValue(),
                       "scope", scope)
        );

        if (token == null) {
            throw new PerobobbotException("Could not retrieve token : null body");
        }

        return token.toAppAccessToken(now);
    }

    @Override
    public @NonNull String getAuthorizationCodeFlowURI(@NonNull ImmutableSet<Scope> scopes) {
        return Todo.TODO();
    }
}
