package perobobbot.discord.gateway.impl.state.connection;

import perobobbot.lang.PerobobbotException;

public class ConnectionError extends PerobobbotException {

    public ConnectionError(Throwable cause) {
        super("The connection to the gateway failed "+cause.getMessage(), cause);
    }

    public ConnectionError(String message) {
        super(message);
    }
    public ConnectionError(String message, Throwable cause) {
        super(message,cause);
    }
}
