package bot.chat.advanced;

import lombok.NonNull;

import java.util.Optional;

/**
 * Convert a text message from the chat to
 * a typed message
 * @author perococco
 * @param <M> the type of the message received from the chat
 **/
public interface MessageConverter<M> {

    /**
     * Convert the text message to the type of the messages of the chat
     * @param messageAsString the text message
     * @return an optional containing the converted message, an empty optional
     * if the conversion could not be done
     */
    @NonNull
    Optional<M> convert(@NonNull String messageAsString);
}
