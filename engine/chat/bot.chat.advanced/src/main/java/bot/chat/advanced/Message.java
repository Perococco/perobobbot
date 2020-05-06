package bot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Message {

    @NonNull
    String payload(@NonNull DispatchContext dispatchContext);
}
