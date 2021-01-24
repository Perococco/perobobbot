package perobobbot.security.com;

import lombok.Value;
import perobobbot.lang.TypeScript;


/**
 * @author Perococco
 */
@Value
@TypeScript
public class Credential {

    String login;

    String password;

}
