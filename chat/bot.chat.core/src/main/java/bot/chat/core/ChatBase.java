package bot.chat.core;

import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * Chat implementing the listener part of the Chat interface.
 * It provides two methods to warn the listener that can be
 * used by class extending this :
 *
 * <ol>
 *     <li>{@link #warnListenerOnError(Throwable)}</li>
 *     <li>{@link #warnListenerOnReceivedMessage(String)}</li>
 * </ol>
 *
 *
 * @author perococco
 **/
public abstract class ChatBase implements Chat {

    private final Listeners<ChatListener> listeners = new Listeners<>();

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return listeners.addListener(listener);
    }

    protected void warnListenerOnError(@NonNull Throwable error) {
        listeners.warnListeners(ChatListener::onError,error);
    }

    protected void warnListenerOnReceivedMessage(@NonNull String receivedMessage) {
        listeners.warnListeners(ChatListener::onReceivedMessage,receivedMessage);
    }

    protected void warnListenerOnPostMessage(@NonNull String postMessage) {
        listeners.warnListeners(ChatListener::onPostMessage,postMessage);
    }

}
