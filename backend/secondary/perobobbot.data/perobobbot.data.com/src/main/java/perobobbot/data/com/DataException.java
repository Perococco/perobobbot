package perobobbot.data.com;

import perobobbot.lang.PerobobbotException;

public class DataException extends PerobobbotException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
