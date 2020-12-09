package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwicthChatNotConnected;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;
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
    public @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel) {
        throw new TwicthChatNotConnected();
    }

}
