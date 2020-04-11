package bot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface MessageConverter {

    @NonNull
    Message convert(@NonNull String messageAsString);
}
