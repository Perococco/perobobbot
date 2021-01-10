package perobobbot.security.com;

import lombok.Value;
import perobobbot.lang.TypeScript;


/**
 * @author Perococco
 * @version 15/04/2019
 */
@Value
@TypeScript
public class Credential {

    String login;

    String password;

}
