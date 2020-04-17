package bot.chat.advanced;

import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public interface MessageConverter<M> {

    @NonNull
    Optional<M> convert(@NonNull String messageAsString);
}
