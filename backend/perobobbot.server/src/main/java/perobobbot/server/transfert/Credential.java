package perobobbot.server.transfert;

import lombok.NonNull;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import perobobbot.lang.DTO;

import javax.validation.constraints.NotBlank;

/**
 * @author Perococco
 * @version 15/04/2019
 */
@Value
@DTO
public class Credential {

    @NotBlank String login;

    @NotBlank String password;

    @NonNull
    public Authentication createAuthentication() {
        return new UsernamePasswordAuthenticationToken(login, password);
    }

}