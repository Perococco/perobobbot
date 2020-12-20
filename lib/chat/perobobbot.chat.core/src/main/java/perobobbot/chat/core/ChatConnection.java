package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface ChatConnection {

    @NonNull Platform getPlatform();

    /**
     * @return the bot used for the connection
     */
    @NonNull Bot getBot();

    /**
     * join a channel
     * @param channelName the name of the channel to join
     * @return a {@link CompletionStage} containing the {@link MessageChannelIO} after successful connection
     */
    @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName);

    @NonNull CompletionStage<? extends Optional<? extends MessageChannelIO>> findChannel(@NonNull String channelName);

    default @NonNull CompletionStage<? extends MessageChannelIO> getChannel(@NonNull String channelName) {
        return findChannel(channelName)
                .thenApply(o -> o.orElseThrow(() -> new ChatChannelNotJoined(getBot(),getPlatform(),channelName)));
    }

}
