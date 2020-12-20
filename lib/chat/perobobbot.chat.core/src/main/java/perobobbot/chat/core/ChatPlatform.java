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
     * @param bot the bot to use to connect to the chat platform
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO} after successful connection
     */
    @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull Bot bot);

    /**
     * find the chat connection for the specific authentication
     * @param bot the botId used to connect
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO}
     */
    @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull Bot bot);

    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

    default @NonNull CompletionStage<? extends ChatConnection> getConnection(@NonNull Bot bot) {
        return findConnection(bot)
                .orElseGet(() -> CompletableFuture.failedFuture(new ChatConnectionNotDone(getPlatform(), bot)));
    }

    default @NonNull CompletionStage<? extends MessageChannelIO> getChannelIO(@NonNull Bot bot, @NonNull String channelName) {
        return getConnection(bot).thenCompose(c -> c.getChannel(channelName));
    }

    void dispose();
}
