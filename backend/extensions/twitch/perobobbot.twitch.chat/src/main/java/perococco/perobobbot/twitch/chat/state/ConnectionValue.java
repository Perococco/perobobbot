package perococco.perobobbot.twitch.chat.state;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.Subscription;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwicthChatNotConnected;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.from.UserState;
import perococco.perobobbot.twitch.chat.IO;
import perococco.perobobbot.twitch.chat.actions.IOAction;

import java.util.concurrent.CompletionStage;
import java.util.function.UnaryOperator;

@lombok.Value
@Builder(toBuilder = true)
public class ConnectionValue implements TwitchChatState {

    @NonNull
    public static ConnectionValue disconnected() {
        return DISCONNECTED;
    }

    private static final ConnectionValue DISCONNECTED = new ConnectionValue(
            ConnectionIdentity.State.DISCONNECTED,
            null,
            null,
            ImmutableMap.of(),
            Subscription.NONE
    );


    @NonNull ConnectionIdentity.State state;

    IO io;

    String userName;

    @NonNull ImmutableMap<Channel, UserState> joinedChannels;

    @NonNull Subscription subscription;


    @NonNull
    public IO io() {
        if (io == null) {
            throw new TwicthChatNotConnected();
        }
        return io;
    }


    @NonNull
    public ImmutableMap<Channel, UserState> joinedChannels() {
        return joinedChannels;
    }

    @Override
    public boolean connected() {
        return state == ConnectionIdentity.State.CONNECTED;
    }

    public @NonNull String userName() {
        return userName == null ? "": userName;
    }

    @NonNull
    public ConnectionValue withAddedJoinedChannel(@NonNull UserState userState) {
        return withJoinedChannelMutation(MapTool.adder(userState.getChannel(), userState));
    }

    @NonNull
    public ConnectionValue withRemovedJoinedChannel(@NonNull Channel channel) {
        return withJoinedChannelMutation(MapTool.remover(channel));
    }

    @NonNull
    private ConnectionValue withJoinedChannelMutation(@NonNull UnaryOperator<ImmutableMap<Channel, UserState>> mutator) {
        final ImmutableMap<Channel, UserState> newChannels = mutator.apply(joinedChannels);
        if (newChannels == joinedChannels) {
            return this;
        }
        return toBuilder().joinedChannels(newChannels).build();
    }


    @NonNull
    public <T> CompletionStage<T> execute(@NonNull IOAction<T> action) {
        return action.evaluate(io());
    }
}
