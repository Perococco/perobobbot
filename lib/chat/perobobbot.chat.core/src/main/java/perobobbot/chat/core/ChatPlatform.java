package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Platform;
import perobobbot.lang.Subscription;

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
     * @param authentication the authentication used to connect to the chat platform
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO} after successful connection
     */
    @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull ChatAuthentication authentication);

    /**
     * find the chat connection for the specific authentication
     * @param nick the user used to connect
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO}
     */
    @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull String nick);

    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

    default @NonNull CompletionStage<? extends ChatConnection> getConnection(@NonNull String nick) {
        return findConnection(nick)
                .orElseGet(() -> CompletableFuture.failedFuture(new ChatConnectionNotDone(getPlatform(),nick)));
    }

    default @NonNull CompletionStage<? extends MessageChannelIO> getChannelIO(@NonNull String nick, @NonNull String channelName) {
        return getConnection(nick).thenCompose(c -> c.getChannel(channelName));
    }

}
