package bot.chat.core;

import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * @author perococco
 **/
public abstract class ChatClientBase implements ChatClient {

    private final Listeners<ChatClientListener> listeners = new Listeners<>();

    @Override
    @NonNull
    public Subscription addChatClientListener(@NonNull ChatClientListener listener) {
        return listeners.addListener(listener);
    }

    protected void warnListenerOnConnection(@NonNull Chat chat) {
        listeners.warnListeners(ChatClientListener::onConnection,chat);
    }

    protected void warnListenerOnDisconnection() {
        listeners.warnListeners(ChatClientListener::onDisconnection);
    }
}
