package perococco.bot.chat.core;

import bot.chat.core.*;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import lombok.NonNull;
import lombok.Synchronized;

/**
 * @author perococco
 **/
public class ChatBridge implements Chat, ChatListener {

    private Chat proxy = null;

    @NonNull
    private final Listeners<ChatListener> listeners = new Listeners<>();

    @NonNull
    private Subscription subscription = Subscription.NONE;

    @Override
    public void postMessage(@NonNull String message) {
        getProxy().postMessage(message);
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return listeners.addListener(listener);
    }

    private Chat getProxy() {
        final Chat chat = proxy;
        if (chat == null) {
            final ChatNotConnected chatNotConnected = new ChatNotConnected();
            onError(chatNotConnected);
            throw chatNotConnected;
        }
        return chat;
    }

    @Override
    public void onReceivedMessage(@NonNull String receivedMessage) {
        listeners.warnListeners(ChatListener::onReceivedMessage, receivedMessage);
    }

    @Override
    public void onPostMessage(@NonNull String postMessage) {
        listeners.warnListeners(ChatListener::onPostMessage, postMessage);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        listeners.warnListeners(ChatListener::onError,throwable);
    }


    @Synchronized
    public void setProxy(@NonNull Chat chat) {
        this.subscription.unsubscribe();
        this.proxy = chat;
        this.subscription = this.proxy.addChatListener(this);
    }

    @Synchronized
    public void clearProxy() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
        this.proxy = null;
    }

    @Synchronized
    public boolean isConnected() {
        return proxy != null;
    }
}
