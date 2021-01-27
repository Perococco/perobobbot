package perobobbot.messaging;

import lombok.NonNull;
import perobobbot.lang.*;
import perococco.messaging.PerococcoChatController;

/**
 * Control and dispatch to listeners, messages coming from any chat.
 * Use the {@link ChannelInfo} member in the provided contexts to get information
 * on the origin of the message.
 */
public interface ChatController extends MessageDispatcher {

    static @NonNull ChatController create() {
        return new PerococcoChatController();
    }

    /**
     * Called with incoming message from the chat
     * @param messageContext the information about the incoming message
     */
    void handleMessage(@NonNull MessageContext messageContext);

}
