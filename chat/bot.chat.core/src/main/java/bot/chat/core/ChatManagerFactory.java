package bot.chat.core;

import lombok.NonNull;
import perococco.bot.chat.core.DispatcherChatManagerFactory;

import java.net.URI;
import java.util.Optional;

public abstract class ChatManagerFactory {

    /**
     * Create a chat manager with the provided parameter
     * @param address the address of the chat
     * @param reconnectionPolicy the reconnection policy
     * @return a optional containing the chat or en empty optional if this factory cannot handle
     * the provided parameters
     */
    @NonNull
    public abstract ChatManager create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy);

    public abstract boolean canHandle(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy);

    @NonNull
    protected RuntimeException buildCannotHandleException(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        return new IllegalArgumentException("Cannot create chat with provided parameters : address="+address);
    }

    @NonNull
    public static ChatManagerFactory getInstance() {
        return DispatcherChatManagerFactory.getInstance();
    }
}
