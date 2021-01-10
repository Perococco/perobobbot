package perobobbot.security.core.jwt;


import org.springframework.security.core.AuthenticationException;

/**
 * @author Perococco
 */
public class InvalidJWTIssuer extends AuthenticationException {

    public InvalidJWTIssuer(String msg) {
        super(msg);
    }
}
