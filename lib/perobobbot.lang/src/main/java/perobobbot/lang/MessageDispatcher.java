package perobobbot.lang;

import lombok.NonNull;

public interface MessageDispatcher {

    String VERSION = "1.0.0";

    @NonNull Subscription addPreprocessor(@NonNull MessagePreprocessor messagePreprocessor);

    /**
     * @param messageHandler a listener that is called with all incoming from the chats
     * @return a subscription that can be used to remove this message from this chat controller
     */
    @NonNull Subscription addListener(@NonNull MessageHandler messageHandler);

}
