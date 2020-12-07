package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwicthChatNotConnected;
import perococco.perobobbot.twitch.chat.TwitchChannelIO;
import perococco.perobobbot.twitch.chat.TwitchIO;

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
    public @NonNull Optional<TwitchChannelIO> findChannel(@NonNull Channel channel) {
        throw new TwicthChatNotConnected();
    }

    @Override
    public @NonNull ConnectedState asConnectedState() {
        throw new TwicthChatNotConnected();
    }
}
