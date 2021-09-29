package perobobbot.server.config.security;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UserService;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Function1;
import perobobbot.oauth.*;
import perobobbot.security.com.*;
import perobobbot.security.core.jwt.JWTokenManager;
import perobobbot.server.config.security.jwt.JwtTokenFromUserIdentityCreator;

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
    OAuthService oAuthService;
    private final @NonNull
    @EventService
    ClientService clientService;
    private final @NonNull
    @EventService
    UserService userService;
    private final @NonNull JWTokenManager jwTokenManager;


    @Override
    public @NonNull JwtInfo getOpenIdUser(@NonNull UUID id) throws Throwable {
        final var data = oAuthSignIn.getOAuthData(id);

        return data.getFutureToken()
                   .thenApply(OAuthToken::jwtInfo)
                   .toCompletableFuture()
                   .get();
    }

    @Override
    public @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull OAuthUrlOptions options) {
        final var client = clientService.getClient(openIdPlatform);
        final var userOAuthInfo = oAuthManager.prepareUserOAuth(client,options);

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
                         .thenApply(userIdentity -> createOAuthToken(userIdentity, openIdPlatform,token))
                         .whenComplete(this::saveUserToken);
    }

    private OAuthToken createOAuthToken(@NonNull UserIdentity userIdentity, @NonNull Platform openIdPlatform, @NonNull Token token) {
        final var jwtInfo = JwtTokenFromUserIdentityCreator.create(jwTokenManager,userService,userIdentity.getLogin(),openIdPlatform);
        return new OAuthToken(token, openIdPlatform, userIdentity.getUserId(), jwtInfo);
    }


    private void saveUserToken(OAuthToken token, Throwable error) {
        if (token != null) {
            oAuthService.updateUserToken(token.getUserLogin(), token.platform(), token.userId(), token.token());
        }
    }

}
