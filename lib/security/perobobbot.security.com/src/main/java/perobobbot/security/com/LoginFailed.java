package perobobbot.security.com;

import perobobbot.lang.PerobobbotException;

public class LoginFailed extends PerobobbotException {

    public LoginFailed(String message) {
        super(message);
    }

    public LoginFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
