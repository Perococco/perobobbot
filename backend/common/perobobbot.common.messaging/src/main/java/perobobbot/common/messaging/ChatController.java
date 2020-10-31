package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.MessageContext;
import perobobbot.common.lang.MessageHandler;
import perobobbot.common.lang.Subscription;
import perococco.perobobbot.common.messaging.PerococcoChatControllerBuilder;

/**
 * Control and dispatch to command and listener messages coming from any chat.
 * Use the {@link ChannelInfo} member if the provided contexts to get information
 * on the origin of the message.
 */
public interface ChatController {

    /**
     * Called with incomming message from the chat
     * @param messageContext the information about the incoming message
     */
    void handleMessage(@NonNull MessageContext messageContext);

    /**
     * add a chat command that could be executed on incoming message
     * @param chatCommand the command
     * @return a subscription that can be used to remove the command from this chat controller
     */
    @NonNull
    Subscription addCommand(@NonNull ChatCommand chatCommand);

    /**
     * @param handler a listener that is called with all incoming from the chats
     * @return a subscription that can be used to remove this message from this chat controller
     */
    @NonNull
    Subscription addListener(@NonNull MessageHandler handler);

    /**
     * @return a builder to create a new chat controller
     */
    @NonNull
    static ChatControllerBuilder builder() {
        return new PerococcoChatControllerBuilder();
    }
}
