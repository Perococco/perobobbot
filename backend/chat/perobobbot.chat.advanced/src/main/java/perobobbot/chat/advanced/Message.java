package perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.common.lang.DispatchContext;

/**
 * @author perococco
 **/
public interface Message {

    @NonNull
    String payload(@NonNull DispatchContext dispatchContext);
}
