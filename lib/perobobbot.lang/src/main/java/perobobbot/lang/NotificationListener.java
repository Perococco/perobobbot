package perobobbot.lang;

import lombok.NonNull;

/**
 * Handle incoming message from any chat. Must be register to the chat controller
 */
public interface NotificationListener {

    int DEFAULT_PRIORITY = 100;

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * @param notification the incoming notification
     */
    void onMessage(@NonNull Notification notification);
}
