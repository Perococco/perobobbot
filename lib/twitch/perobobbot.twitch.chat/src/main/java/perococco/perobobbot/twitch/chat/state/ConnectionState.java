package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChatState;
import perococco.perobobbot.twitch.chat.TwitchChannelIO;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.Optional;

public interface ConnectionState extends TwitchChatState {

    @NonNull
    static DisconnectedState disconnected() {
        return DisconnectedState.create();
    }

    @NonNull TwitchIO getTwitchIO();

    @Override
    boolean isConnected();

    @NonNull String getUserId();

    @NonNull Optional<TwitchChannelIO> findChannel(@NonNull Channel channel);

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    @NonNull ConnectedState asConnectedState();

    interface Visitor<T> {

        @NonNull T visit(@NonNull DisconnectedState state);
        @NonNull T visit(@NonNull ConnectingState state);
        @NonNull T visit(@NonNull ConnectedState state);

        default @NonNull T applyTo(@NonNull ConnectionState connectedState) {
            return connectedState.accept(this);
        }
    }
}
