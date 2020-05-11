package perobobbot.twitch.chat;

public class TwitchChatException extends RuntimeException {

    public TwitchChatException() {
    }

    public TwitchChatException(String message) {
        super(message);
    }

    public TwitchChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwitchChatException(Throwable cause) {
        super(cause);
    }

}
