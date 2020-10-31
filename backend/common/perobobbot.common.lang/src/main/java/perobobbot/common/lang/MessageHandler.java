package perobobbot.common.lang;

import lombok.NonNull;

/**
 * Handle incoming message from any chat. Must be register to the chat controller
 */
public interface MessageHandler {

    /**
     * @param messageContext the incoming message
     * @return true if the message should not be handle by anyother {@link MessageHandler}
     */
    boolean handleMessage(@NonNull MessageContext messageContext);
}
