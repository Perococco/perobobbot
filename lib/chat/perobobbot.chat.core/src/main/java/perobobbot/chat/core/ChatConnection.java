package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface ChatConnection {

    @NonNull Platform getPlatform();

    @NonNull String getNick();

    /**
     * join a channel
     * @param channelName the name of the channel to join
     * @return a {@link CompletionStage} containing the {@link ChannelIO} after successful connection
     */
    @NonNull CompletionStage<? extends ChannelIO> join(@NonNull String channelName);

    @NonNull CompletionStage<? extends Optional<? extends ChannelIO>> findChannel(@NonNull String channelName);

    default @NonNull CompletionStage<? extends ChannelIO> getChannel(@NonNull String channelName) {
        return findChannel(channelName)
                .thenApply(o -> o.orElseThrow(() -> new ChatChannelNotJoined(getPlatform(),getNick(),channelName)));
    }

}
