package perobobbot.chat.core;

import lombok.NonNull;

/**
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
