package perobobbot.server.config.security;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UserService;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Function1;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserIdentity;
import perobobbot.security.com.*;
import perobobbot.security.core.jwt.JWTokenManager;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Log4j2
@RequiredArgsConstructor
@Component
@PluginService(type = OAuthAuthorizationCodeFlow.class, sensitive = false, apiVersion = OAuthAuthorizationCodeFlow.VERSION)
public class SpringOAuthAuthorizationCodeFlow implements OAuthAuthorizationCodeFlow {

    private final @NonNull OAuthSignIn oAuthSignIn;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull
    @EventService
    ClientService clientService;
    private final @NonNull
    @EventService
    UserService userService;
    private final @NonNull JWTokenManager jwTokenManager;

    @Override
    public @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull String... scopes) {
        return oauthWith(openIdPlatform, ImmutableSet.copyOf(scopes));
    }

    public @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull ImmutableSet<String> scopes) {
        return oauthWith(openIdPlatform, c -> c.mapScope(scopes));
    }

    public @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform) {
        return oauthWith(openIdPlatform, OAuthController::getDefaultScopes);
    }

    private @NonNull OAuthData oauthWith(Platform openIdPlatform,
                                         @NonNull Function1<? super OAuthController, ? extends ImmutableSet<? extends Scope>> scopeProvider) {
        final var controller = oAuthManager.getController(openIdPlatform);
        final var client = clientService.getClient(openIdPlatform);
        final var scopes = scopeProvider.apply(controller);

        final var userOAuthInfo = controller.prepareUserOAuth(client, scopes);
        final var jwtTokenFuture = userOAuthInfo.getFutureToken()
                                                .thenCompose(token -> formOAuthToken(openIdPlatform, token));

        final var id = oAuthSignIn.addPendingSignIn(openIdPlatform, jwtTokenFuture);

        final var info = new OAuthInfo(userOAuthInfo.getOauthURI(), id);
        return new OAuthData(info, jwtTokenFuture);
    }

    private @NonNull CompletionStage<OAuthToken> formOAuthToken(@NonNull Platform openIdPlatform, @NonNull Token token) {
        final var controller = oAuthManager.getController(openIdPlatform);
        final var client = clientService.getClient(openIdPlatform);
        return controller.getUserIdentity(client, token.getAccessToken())
                         .thenApply(userIdentity -> getJwtTokenFromUserIdentity(userIdentity, openIdPlatform))
                         .thenApply(jwt -> new OAuthToken(token, jwt));
    }

    public @NonNull JwtInfo getOpenIdUser(@NonNull UUID id) throws Throwable {
        final var data = oAuthSignIn.getOAuthData(id);

        return data.getFutureToken()
                   .thenApply(OAuthToken::jwtInfo)
                   .toCompletableFuture()
                   .get();
    }

    private @NonNull JwtInfo getJwtTokenFromUserIdentity(@NonNull UserIdentity userIdentity, @NonNull Platform platform) {
        final var login = userIdentity.getLogin();
        final var user = userService.findUser(login).orElse(null);

        if (user == null) {
            final var parameter = new CreateUserParameters(login, Identification.openId(platform));
            userService.createUser(parameter);
        } else {
            final var userPlatform = user.getIdentificationOpenIdPlatform().orElse(null);
            if (!platform.equals(userPlatform)) {
                LOG.warn("Invalid platform used to identify user '{}': expected='{}' actual='{}'", login, userPlatform, platform);
                throw new BadCredentialsException("A user exists with this login already");
            }
        }

        return jwTokenManager.createJwtInfo(login);
    }


}
