package perococco.bot.twitch.chat.state;

import bot.common.lang.MapTool;
import bot.common.lang.Subscription;
import bot.twitch.chat.Channel;
import bot.twitch.chat.TwicthChatNotConnected;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import perococco.bot.twitch.chat.IO;
import perococco.bot.twitch.chat.actions.IOAction;

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
            false,
            ImmutableMap.of(),
            Subscription.NONE
    );


    @NonNull
    private final ConnectionIdentity.State state;

    private final IO io;

    private final String username;

    private final boolean userIsModerator;

    @NonNull
    private final ImmutableMap<Channel, UserState> joinedChannels;

    @NonNull
    private final Subscription subscription;


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

    public @NonNull String username() {
        return username == null ? "":username;
    }

    @NonNull
    public ConnectionValue withAddedJoinedChannel(@NonNull UserState userState) {
        return withJoinedChannelMutation(MapTool.adder(userState.channel(),userState));
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
