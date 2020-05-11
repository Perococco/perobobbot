package perbobbot.blackjack.engine.exception;

public class BlackjackException extends RuntimeException {

    public BlackjackException(String message) {
        super(message);
    }

    public BlackjackException(String message, Throwable cause) {
        super(message, cause);
    }
}
