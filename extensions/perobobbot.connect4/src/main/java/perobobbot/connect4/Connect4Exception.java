package perobobbot.connect4;

import perobobbot.lang.PerobobbotException;

public class Connect4Exception extends PerobobbotException {

    public Connect4Exception(String message) {
        super(message);
    }

    public Connect4Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
