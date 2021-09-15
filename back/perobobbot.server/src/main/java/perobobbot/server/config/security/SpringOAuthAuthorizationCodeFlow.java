package perobobbot.server.config.security;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import perobobbot.oauth.UserIdentity;
import perobobbot.security.com.*;
import perobobbot.security.core.jwt.JWTokenManager;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
@PluginService(type = OAuthAuthorizationCodeFlow.class, sensitive = false, apiVersion = OAuthAuthorizationCodeFlow.VERSION)
public class SpringOAuthAuthorizationCodeFlow implements OAuthAuthorizationCodeFlow {

    private final @NonNull OAuthSignIn oAuthSignIn;
    private final @NonNull OAuthManager oAuthManager;
    private final @NonNull @EventService ClientService clientService;
    private final @NonNull @EventService UserService userService;
    private final @NonNull JWTokenManager jwTokenManager;

    @Override
    public @NonNull OAuthInfo oauthWith(@NonNull Platform openIdPlatform, @NonNull String... scopes) {
        return oauthWith(openIdPlatform, ImmutableSet.copyOf(scopes));
    }

    public @NonNull OAuthInfo oauthWith(@NonNull Platform openIdPlatform, @NonNull ImmutableSet<String> scopes) {
        return oauthWith(openIdPlatform, c -> c.mapScope(scopes));
    }

    public @NonNull OAuthInfo oauthWith(@NonNull Platform openIdPlatform) {
        return oauthWith(openIdPlatform, OAuthController::getDefaultScopes);
    }

    private @NonNull OAuthInfo oauthWith(Platform openIdPlatform,
                                         @NonNull Function1<? super OAuthController, ? extends ImmutableSet<? extends Scope>> scopeProvider) {
        final var controller = oAuthManager.getController(openIdPlatform);
        final var client = clientService.getClient(openIdPlatform);
        final var scopes = scopeProvider.apply(controller);

        final var userOAuthInfo = controller.prepareUserOAuth(client, scopes);
        final var id = oAuthSignIn.addPendingSignIn(openIdPlatform, userOAuthInfo.getFutureToken());

        return new OAuthInfo(userOAuthInfo.getOauthURI(), id);
    }

    public @NonNull JwtInfo getOpenIdUser(@NonNull UUID id) throws Throwable {
        final var data = oAuthSignIn.getOAuthData(id);
        final var platform = data.getPlatform();
        final var controller = oAuthManager.getController(platform);
        final var client = clientService.getClient(platform);

        final var futureJwt = data.getFutureToken()
                                  .thenCompose(token -> controller.getUserIdentity(client, token.getAccessToken()))
                                  .thenApply(userIdentity -> getJwtTokenFromUserIdentity(userIdentity,platform));

        return futureJwt.toCompletableFuture().get();
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
