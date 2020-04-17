package perococco.bot.twitch.chat;

import bot.chat.advanced.AdvancedChat;
import bot.common.lang.SetTool;
import bot.common.lang.Subscription;
import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatOAuth;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.UnaryOperator;

@lombok.Value
@Builder(toBuilder = true)
class ConnectionValue implements TwitchChatState {

    @NonNull
    public static ConnectionValue disconnected() {
        return DISCONNECTED;
    }

    private static final ConnectionValue DISCONNECTED = new ConnectionValue(ConnectionIdentity.State.DISCONNECTED, null, null, null,Subscription.NONE);


    @NonNull
    private final ConnectionIdentity.State state;

    private final TwitchChatOAuth oAuth;

    private final AdvancedChat<MessageFromTwitch> chat;

    private final ImmutableSet<Channel> joinedChannels;

    @NonNull
    private final Subscription subscription;


    @NonNull
    public Optional<AdvancedChat<MessageFromTwitch>> chat() {
        return Optional.ofNullable(chat);
    }

    @NonNull
    public Optional<TwitchChatOAuth> oAuth() {
        return Optional.ofNullable(oAuth);
    }

    @NonNull
    public Optional<ImmutableSet<Channel>> joinedChannels() {
        return Optional.ofNullable(joinedChannels);
    }

    @Override
    public boolean connected() {
        return state == ConnectionIdentity.State.CONNECTED;
    }

    @Override
    public @NonNull String userNickName() {
        return oAuth == null ? "":oAuth.nick();
    }

    @NonNull
    public ConnectionValue withAddedJoinedChannel(@NonNull Channel channel) {
        return withJoinedChannelMutation(SetTool.adder(channel));
    }

    @NonNull
    public ConnectionValue withRemovedJoinedChannel(@NonNull Channel channel) {
        return withJoinedChannelMutation(SetTool.remover(channel));
    }

    private ConnectionValue withJoinedChannelMutation(@NonNull UnaryOperator<ImmutableSet<Channel>> mutator) {
        if (joinedChannels == null) {
            return this;
        }
        final ImmutableSet<Channel> newChannels = mutator.apply(joinedChannels);
        if (newChannels == joinedChannels) {
            return this;
        }
        return toBuilder().joinedChannels(newChannels).build();
    }
}
