package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.Todo;
import perobobbot.oauth.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class TwitchOAuthController implements OAuthController {


    public static final String TWITCH_OAUTH_PATH = "/twitch/oauth";

    private final @NonNull OAuthSubscriptions oAuthSubscriptions;
    private final @NonNull RestOperations restOperations;


    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }


    @Override
    public @NonNull CompletionStage<Token> getAppToken(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes) {
        return Todo.TODO();
    }

    @Override
    public @NonNull UserOAuthInfo prepareUserOAuth(@NonNull String clientId, @NonNull Secret clientSecret, ImmutableSet<? extends Scope> scopes) {
        final var listener = new OAuthAuthorizationListener(restOperations,  clientId,clientSecret);

        final var subscriptionData = oAuthSubscriptions.subscribe(TWITCH_OAUTH_PATH, listener);
        final var oauthURI = buildURI(clientId,scopes,subscriptionData);

        return new UserOAuthInfo(oauthURI, listener.getFutureToken());
    }

    private @NonNull URI buildURI(@NonNull String clientId, ImmutableSet<? extends Scope> scopes, @NonNull SubscriptionData subscriptionData) {
        return UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/authorize")
                            .queryParam("client_id",clientId)
                            .queryParam("redirect_uri",subscriptionData.getOAuthRedirectURI())
                            .queryParam("response_type","code")
                            .queryParam("scope",Scope.scopeNamesSpaceSeparated(scopes))
                            .queryParam("state",subscriptionData.getState())
                            .build().toUri();
    }

}
