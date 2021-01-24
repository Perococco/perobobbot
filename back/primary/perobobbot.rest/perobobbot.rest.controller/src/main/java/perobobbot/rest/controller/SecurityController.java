package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.security.com.Credential;
import perobobbot.security.com.EndPoints;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.SimpleUser;
import perobobbot.security.core.LoginFromAuthentication;
import perobobbot.security.core.jwt.JWTokenManager;

import javax.validation.Valid;
/**
 * @author Perococco
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    private final @NonNull AuthenticationManager authenticationManager;

    private final @NonNull @SecuredService UserService userService;

    private final @NonNull JWTokenManager jwTokenManager;

    /**
     * @param principal the principal provided by the security framework if an user is authenticated
     * @return the authenticated user information
     */
    @GetMapping(EndPoints
            .CURRENT_USER)
    public SimpleUser getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
        return userService.getUser(principal.getUsername()).simplify();
    }

    /**
     * Authenticate the user with the provided credential and return a JWT for further authentication
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
     * @param parameters the data used to create the user
     * @return the created user
     */
    @PostMapping(EndPoints.SIGN_UP)
    public SimpleUser singup(@RequestBody CreateUserParameters parameters) {
        return userService.createUser(parameters)
                          .simplify();
    }

}
