package perobobbot.lang;

import lombok.NonNull;

/**
 * Handle incoming message from any chat. Must be register to the chat controller
 */
public interface MessageListener {

    int DEFAULT_PRIORITY = 100;

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * @param messageContext the incoming message
     */
    void onMessage(@NonNull MessageContext messageContext);
}
