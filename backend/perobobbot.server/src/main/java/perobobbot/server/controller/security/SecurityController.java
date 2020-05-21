package perobobbot.server.controller.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UserDTO;
import perobobbot.data.service.UserService;
import perobobbot.server.EndPoints;
import perobobbot.server.transfert.Credentials;

import javax.validation.Valid;
/**
 * @author Bastien Aracil
 * @version 14/04/2019
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecurityController {

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final UserService userService;

    @GetMapping(EndPoints.CURRENT_USER)
    public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
        return userService.getUserInfo(principal.getUsername());
    }

    /**
     * Authenticate the user with the provided credential and return a JWT for further authentication
     * @param credentials the credentials to use to authenticate the user
     * @return a JWT for further authentication
     */
    @PostMapping(EndPoints.LOGIN)
    public String login(@Valid @RequestBody Credentials credentials) {
        final Authentication authentication = authenticationManager.authenticate(credentials.createAuthentication());
        final LoginFromAuthentication email = new LoginFromAuthentication(authentication);
        return userService.getJWTToken(email.toString());
    }


    /**
     * @param parameters the data used to create the user
     * @return the created user
     */
    @PostMapping(EndPoints.SIGN_UP)
    public UserDTO singup(@RequestBody CreateUserParameters parameters) {
        return userService.createUser(parameters);
    }

}
