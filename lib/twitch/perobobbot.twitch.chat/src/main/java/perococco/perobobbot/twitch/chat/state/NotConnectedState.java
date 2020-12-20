package perococco.perobobbot.twitch.chat.state;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bot;
import perobobbot.lang.ChannelInfo;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwicthChatNotConnected;
import perococco.perobobbot.twitch.chat.TwitchIO;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;

import java.util.Optional;

public abstract class NotConnectedState implements ConnectionState {

    @Override
    public @NonNull TwitchIO getTwitchIO() {
        throw new TwicthChatNotConnected();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public @NonNull String getUserId() {
        throw new TwicthChatNotConnected();
    }

    @Override
    public @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel) {
        throw new TwicthChatNotConnected();
    }

    @Override
    public @NonNull ImmutableSet<ChannelInfo> getJoinedChannels() {
        return ImmutableSet.of();
    }

    @Override
    public boolean hasJoined(@NonNull String channelName) {
        return false;
    }
}
