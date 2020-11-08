package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.MessageContext;
import perobobbot.common.lang.MessageHandler;
import perobobbot.common.lang.Subscription;
import perococco.common.messaging.PerococcoChatController;

/**
 * Control and dispatch to command and listener messages coming from any chat.
 * Use the {@link ChannelInfo} member if the provided contexts to get information
 * on the origin of the message.
 */
public interface ChatController {

    static @NonNull ChatController create() {
        return new PerococcoChatController();
    }

    /**
     * Called with incoming message from the chat
     * @param messageContext the information about the incoming message
     */
    void handleMessage(@NonNull MessageContext messageContext);

    /**
     * @param handler a listener that is called with all incoming from the chats
     * @return a subscription that can be used to remove this message from this chat controller
     */
    @NonNull
    Subscription addListener(@NonNull MessageHandler handler);

}
