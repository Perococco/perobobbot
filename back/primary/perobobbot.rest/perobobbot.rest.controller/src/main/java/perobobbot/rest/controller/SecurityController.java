package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.*;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.UserIdentity;
import perobobbot.rest.com.OAuthInfo;
import perobobbot.security.com.*;
import perobobbot.security.core.LoginFromAuthentication;
import perobobbot.security.core.jwt.JWTokenManager;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author perococco
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    private final @NonNull AuthenticationManager authenticationManager;


    private final @NonNull JWTokenManager jwTokenManager;

    private final @NonNull OAuthManager oAuthManager;

    private final @NonNull
    @SecuredService
    UserService userService;

    private final @NonNull
    @UnsecuredService
    UserService unsecuredUserService;

    private final @EventService
    @NonNull ClientService clientService;

    private final @NonNull OAuthSignIn oAuthSignIn;


    /**
     * @param principal the principal provided by the security framework if an user is authenticated
     * @return the authenticated user information
     */
    @GetMapping(EndPoints.CURRENT_USER)
    public SimpleUser getCurrentUser(@AuthenticationPrincipal BotUser principal) {
        return userService.getUser(principal.getUsername()).simplify();
    }

    /**
     * Authenticate the user with the provided credential and return a JWT for further authentication
     *
     * @param credential the credentials to use to authenticate the user
     * @return a JWT for further authentication
     */
    @PostMapping(EndPoints.SIGN_IN)
    public JwtInfo signIn(@Valid @RequestBody Credential credential) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.getLogin(), credential.getPassword()));
        final LoginFromAuthentication login = new LoginFromAuthentication(authentication);
        return jwTokenManager.createJwtInfo(login.toString());
    }

    /**
     * Sign up user
     *
     * @param parameters the data used to create the user
     * @return the created user
     */
    @PostMapping(EndPoints.SIGN_UP)
    public SimpleUser signUp(@RequestBody CreateUserParameters parameters) {
        return userService.createUser(parameters)
                          .simplify();
    }

    @PostMapping(EndPoints.OAUTH)
    public @NonNull OAuthInfo oauthWith(@RequestBody Platform openIdPlatform) {
        final var controller = oAuthManager.getController(openIdPlatform);
        final var client = clientService.getClient(openIdPlatform);

        final var userOAuthInfo = controller.prepareUserOAuth(client);
        final var id = oAuthSignIn.addPendingSignIn(openIdPlatform, userOAuthInfo.getFutureToken());

        return new OAuthInfo(userOAuthInfo.getOauthURI(), id);
    }

    //WARNING security risk !!
    @GetMapping(EndPoints.OAUTH + "/{id}")
    public @NonNull JwtInfo getOpenIdUser(@PathVariable UUID id) throws Throwable {
        final var data = oAuthSignIn.getOAuthData(id);
        final var platform = data.getPlatform();
        final var controller = oAuthManager.getController(platform);
        final var client = clientService.getClient(platform);

        final var futureJwt = data.getFutureToken()
                                  .thenCompose(token -> controller.getUserIdentity(client, token.getAccessToken()))
                                  .thenApply(userIdentity -> getJwtTokenFromUserIdentity(userIdentity,platform));

        return futureJwt.toCompletableFuture().get();
    }


    private @NonNull  JwtInfo getJwtTokenFromUserIdentity(@NonNull UserIdentity userIdentity, @NonNull Platform platform) {
        final var login = userIdentity.getLogin();
        final boolean userExists = unsecuredUserService.doesUserExist(login);

        if (!userExists) {
            final var parameter = new CreateUserParameters(login,Identification.openId(platform));
            unsecuredUserService.createUser(parameter);
        }

        return jwTokenManager.createJwtInfo(login);
    }

}
