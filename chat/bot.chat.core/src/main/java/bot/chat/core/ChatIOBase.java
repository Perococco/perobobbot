package bot.chat.core;

import bot.chat.core.event.ChatEvent;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * Chat implementing the listener part of the Chat interface.
 * It provides a method to warn the listener that can be
 * used by class extending <code>this</code> :
 *
 * <ol>
 *     <li>{@link #warnListeners(ChatEvent)}</li>
 * </ol>
 *
 *
 * @author perococco
 **/
public abstract class ChatIOBase implements ChatIO {

    private final Listeners<ChatListener> listeners = new Listeners<>();

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return listeners.addListener(listener);
    }

    protected void warnListeners(@NonNull ChatEvent event) {
        listeners.warnListeners(ChatListener::onChatEvent, event);
    }

}
