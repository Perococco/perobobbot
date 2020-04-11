package bot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ContextSupplier<C> {

    @NonNull
    C getContext();
}
