package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.*;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.UserIdentity;
import perobobbot.security.com.OAuthInfo;
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
@Log4j2
@Validated
public class SecurityController {

    private final @NonNull AuthenticationManager authenticationManager;

    private final @NonNull JWTokenManager jwTokenManager;

    private final @NonNull
    @SecuredService
    UserService userService;

    private final @NonNull OAuthAuthorizationCodeFlow oAuthAuthorizationCodeFlow;


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

    @PostMapping(EndPoints.PASSWORD_CHANGE)
    public void changePassword(@AuthenticationPrincipal BotUser principal, @RequestBody @Valid ChangePasswordParameters parameters) {
        final var login = principal.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, parameters.getPassword()));
        userService.changePassword(login, parameters.getNewPassword());
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
        return oAuthAuthorizationCodeFlow.oauthWith(openIdPlatform);
    }

    //WARNING security risk !!
    @GetMapping(EndPoints.OAUTH + "/{id}")
    public @NonNull JwtInfo getOpenIdUser(@PathVariable UUID id) throws Throwable {
        return oAuthAuthorizationCodeFlow.getOpenIdUser(id);
    }


}
