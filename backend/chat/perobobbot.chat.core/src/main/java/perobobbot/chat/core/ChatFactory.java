package perobobbot.chat.core;

import lombok.NonNull;
import perococco.perobobbot.chat.core.DispatcherChatFactory;

import java.net.URI;

public abstract class ChatFactory {

    /**
     * Create a chat manager with the provided parameter
     * @param address the address of the chat
     * @param reconnectionPolicy the reconnection policy
     * @return the newly create chat with the provided parameters
     */
    @NonNull
    public abstract Chat create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy);

    /**
     * @param address the address of the chat
     * @param reconnectionPolicy the reconnection policy
     * @return true if this factory can create a chat with the provided parameter, false otherwise
     */
    public abstract boolean canHandle(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy);

    @NonNull
    protected RuntimeException buildCannotHandleException(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        return new IllegalArgumentException("Cannot create chat with provided parameters : address="+address);
    }

    @NonNull
    public static ChatFactory getInstance() {
        return DispatcherChatFactory.getInstance();
    }
}
