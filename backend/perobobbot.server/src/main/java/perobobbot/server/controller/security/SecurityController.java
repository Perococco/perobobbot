package perobobbot.server.controller.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.SimpleUser;
import perobobbot.data.service.UserService;
import perobobbot.server.EndPoints;
import perobobbot.server.config.security.jwt.JWTokenManager;
import perobobbot.server.transfert.Credential;

import javax.validation.Valid;
/**
 * @author Perococco
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    private final @NonNull AuthenticationManager authenticationManager;

    private final @NonNull UserService userService;

    private final @NonNull JWTokenManager jwTokenManager;

    /**
     * @param principal the principal provided by the security framework if an user is authenticated
     * @return the authenticated user information
     */
    @GetMapping(EndPoints.CURRENT_USER)
    public SimpleUser getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
        return userService.getUser(principal.getUsername()).simplify();
    }

    /**
     * Authenticate the user with the provided credential and return a JWT for further authentication
     * @param credential the credentials to use to authenticate the user
     * @return a JWT for further authentication
     */
    @PostMapping(EndPoints.SIGN_IN)
    public String signIn(@Valid @RequestBody Credential credential) {
        final Authentication authentication = authenticationManager.authenticate(credential.createAuthentication());
        final LoginFromAuthentication login = new LoginFromAuthentication(authentication);
        return jwTokenManager.createJWToken(login.toString());
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
