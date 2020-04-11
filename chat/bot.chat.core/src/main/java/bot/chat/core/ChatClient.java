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

    @NonNull
    default ChatClient withReconnection(@NonNull ReconnectionPolicy policy) {
        return ReconnectingChatClientFactory.getInstance().createReconnectingChatClient(this,policy);
    }
}
