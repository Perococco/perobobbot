package perobobbot.lang;

import lombok.NonNull;

/**
 * Handle incoming message from any chat. Must be register to the chat controller
 */
public interface MessageHandler {

    default int priority() {
        return 0;
    }

    /**
     * @param messageContext the incoming message
     * @return true if the message should not be handle by anyother {@link MessageHandler}
     */
    void handleMessage(@NonNull MessageContext messageContext);
}
