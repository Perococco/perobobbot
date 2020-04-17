package bot.chat.core;

import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ChatClient {

    @NonNull
    Chat connect();

    void disconnect();

    boolean isConnected();

    @NonNull
    Subscription addChatClientListener(@NonNull ChatClientListener listener);

    default ChatClient withoutReconnection() {
        return this;
    }

    @NonNull
    default ChatClient withReconnection(@NonNull ReconnectionPolicy policy, @NonNull ReconnectionListener listener) {
        return ReconnectingChatClientFactory.getInstance().createReconnectingChatClient(this,policy);
    }
}
