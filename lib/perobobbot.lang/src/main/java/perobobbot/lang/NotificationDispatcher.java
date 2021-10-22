package perobobbot.lang;

import lombok.NonNull;

public interface NotificationDispatcher {

    int VERSION = 1;

    /**
     * @param messageListener a listener that is called with all incoming from the chats
     * @return a subscription that can be used to remove this message from this chat controller
     */
    @NonNull Subscription addListener(@NonNull NotificationListener messageListener);

    void sendNotification(@NonNull Notification notification);
}
