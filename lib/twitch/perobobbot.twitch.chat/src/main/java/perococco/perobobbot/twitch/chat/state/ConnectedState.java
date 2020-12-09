package perococco.perobobbot.twitch.chat.state;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;
import perobobbot.twitch.chat.Channel;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;
import perococco.perobobbot.twitch.chat.TwitchIO;
import perococco.perobobbot.twitch.chat.actions.TwitchIOAction;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.UnaryOperator;

/**
 * The connected state.
 */
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ConnectedState implements ConnectionState {

    public static ConnectedState create(@NonNull TwitchIO io, @NonNull String userId, @NonNull Subscription subscription) {
        return new ConnectedState(io,userId, ImmutableMap.of(), subscription);
    }

    /**
     * the i/o to communicate with twitch chat
     */
    @Getter
    private final @NonNull TwitchIO twitchIO;

    /**
     * The id of the user used for the connection
     */
    @Getter
    private final @NonNull String userId;

    /**
     * A map of all the joined channels
     */
    private final @NonNull ImmutableMap<Channel, TwitchMessageChannelIO> joinedChannels;

    /**
     *
     */
    @Getter
    private final @NonNull Subscription subscription;

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public @NonNull Optional<ConnectedState> asConnectedState() {
        return Optional.of(this);
    }

    public <T> @NonNull CompletionStage<T> execute(@NonNull TwitchIOAction<T> action) {
        return action.evaluate(this.getTwitchIO());
    }

    public @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel) {
        return Optional.ofNullable(joinedChannels.get(channel));
    }


    public @NonNull ConnectedState withAddedJoinedChannel(@NonNull TwitchMessageChannelIO twitchChannelIO) {
        return withJoinedChannelMutation(MapTool.adder(twitchChannelIO.getChannel(), twitchChannelIO));
    }

    public @NonNull ConnectedState withRemovedJoinedChannel(@NonNull Channel channel) {
        return withJoinedChannelMutation(MapTool.remover(channel));
    }

    private @NonNull ConnectedState withJoinedChannelMutation(@NonNull UnaryOperator<ImmutableMap<Channel, TwitchMessageChannelIO>> mutator) {
        final ImmutableMap<Channel, TwitchMessageChannelIO> newChannels = mutator.apply(joinedChannels);
        if (newChannels == joinedChannels) {
            return this;
        }
        return toBuilder().joinedChannels(newChannels).build();
    }


}