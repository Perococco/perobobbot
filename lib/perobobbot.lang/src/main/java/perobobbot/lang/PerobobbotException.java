package perobobbot.lang;

public class PerobobbotException extends RuntimeException {

    public PerobobbotException(String message) {
        super(message);
    }

    public PerobobbotException(String message, Throwable cause) {
        super(message, cause);
    }
}
