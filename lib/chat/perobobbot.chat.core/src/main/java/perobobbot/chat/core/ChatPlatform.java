package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface ChatPlatform {

    /**
     * @return the platform this i/o applies to
     */
    @NonNull Platform getPlatform();

    /**
     * connect to a chat platform
     * @param chatConnectionInfo the connection information to use to connect to the chat platform
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO} after successful connection
     */
    @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull ChatConnectionInfo chatConnectionInfo);

    /**
     * find the chat connection for the specific authentication
     * @param chatConnectionInfo the information used to connect
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO}
     */
    @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull ChatConnectionInfo chatConnectionInfo);

    /**
     * Add a listener that will be called for all message received on the platform
     * @param listener the listener to add
     * @return a subscription that can be used to remove the listener
     */
    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

    default @NonNull CompletionStage<? extends ChatConnection> getConnection(@NonNull ChatConnectionInfo chatConnectionInfo) {
        return findConnection(chatConnectionInfo)
                .orElseGet(() -> CompletableFuture.failedFuture(new ChatConnectionNotDone(chatConnectionInfo)));
    }

    default @NonNull CompletionStage<? extends MessageChannelIO> getChannelIO(@NonNull ChatConnectionInfo chatConnectionInfo, @NonNull String channelName) {
        return getConnection(chatConnectionInfo).thenCompose(c -> c.getChannel(channelName));
    }

    void dispose();
}
