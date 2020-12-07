package perococco.perobobbot.twitch.chat.state;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;
import perobobbot.twitch.chat.Channel;
import perococco.perobobbot.twitch.chat.TwitchChannelIO;
import perococco.perobobbot.twitch.chat.TwitchIO;
import perococco.perobobbot.twitch.chat.actions.TwitchIOAction;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ConnectedState implements ConnectionState {

    public static ConnectedState create(@NonNull TwitchIO io, @NonNull String userId, @NonNull Subscription subscription) {
        return new ConnectedState(io,userId, ImmutableMap.of(), subscription);
    }

    @Getter
    private final @NonNull TwitchIO twitchIO;

    @Getter
    private final @NonNull String userId;

    private final @NonNull ImmutableMap<Channel, TwitchChannelIO> joinedChannels;

    @Getter
    private final @NonNull Subscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public @NonNull ConnectedState asConnectedState() {
        return this;
    }

    public <T> @NonNull CompletionStage<T> execute(@NonNull TwitchIOAction<T> action) {
        return action.evaluate(this.getTwitchIO());
    }

    public @NonNull Optional<TwitchChannelIO> findChannel(@NonNull Channel channel) {
        return Optional.ofNullable(joinedChannels.get(channel));
    }


    public @NonNull ConnectedState withAddedJoinedChannel(@NonNull TwitchChannelIO twitchChannelIO) {
        return withJoinedChannelMutation(MapTool.adder(twitchChannelIO.getChannel(), twitchChannelIO));
    }

    public @NonNull ConnectedState withRemovedJoinedChannel(@NonNull Channel channel) {
        return withJoinedChannelMutation(MapTool.remover(channel));
    }

    private @NonNull ConnectedState withJoinedChannelMutation(@NonNull UnaryOperator<ImmutableMap<Channel, TwitchChannelIO>> mutator) {
        final ImmutableMap<Channel, TwitchChannelIO> newChannels = mutator.apply(joinedChannels);
        if (newChannels == joinedChannels) {
            return this;
        }
        return toBuilder().joinedChannels(newChannels).build();
    }


}
