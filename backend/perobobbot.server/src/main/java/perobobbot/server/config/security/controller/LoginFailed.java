package perobobbot.server.config.security.controller;

import perobobbot.lang.PerobobbotException;

public class LoginFailed extends PerobobbotException {

    public LoginFailed(String message) {
        super(message);
    }

    public LoginFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
