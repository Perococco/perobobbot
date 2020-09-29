package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.MessageContext;

public interface MessageHandler {

    interface Factory<P> {
        @NonNull
        MessageHandler create(@NonNull P parameter);
    }

    MessageHandler NOP = c -> false;

    /**
     * @param context the message context
     * @return true if the message should not be handled anymore
     */
    boolean handleMessage(@NonNull MessageContext context);
}
