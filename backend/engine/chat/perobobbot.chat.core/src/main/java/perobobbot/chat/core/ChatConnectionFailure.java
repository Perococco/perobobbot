package perobobbot.chat.core;

import lombok.NonNull;

/**
 * Exception thrown when a error occurred during the chat connection
 * @author perococco
 **/
public class ChatConnectionFailure extends ChatException {

    public ChatConnectionFailure(@NonNull String message) {
        super(message);
    }

    public ChatConnectionFailure(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
    }
}
