package perobobbot.chat.core;

/**
 * @author perococco
 **/
public class ChatNotConnected extends ChatException {

    public ChatNotConnected() {
        super("The chat is not connected");
    }

    public ChatNotConnected(String message, Throwable cause) {
        super("The chat is not connected",cause);
    }
}
