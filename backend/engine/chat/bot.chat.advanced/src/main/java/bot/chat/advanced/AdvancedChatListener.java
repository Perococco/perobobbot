package bot.chat.advanced;

import bot.chat.advanced.event.AdvancedChatEvent;
import lombok.NonNull;

/**
 * @author Perococco
 * @param <M> the type of message from the chat
 */
public interface AdvancedChatListener<M> {

    /**
     * Called when an event occurs on the chat
     * @param chatEvent the event that occurred
     */
    void onChatEvent(@NonNull AdvancedChatEvent<M> chatEvent);
}
