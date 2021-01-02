package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ConnectionInfo;
import perobobbot.lang.Subscription;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.Optional;

@RequiredArgsConstructor
public class ConnectingState extends NotConnectedState {

    public static @NonNull ConnectingState create(@NonNull ConnectionInfo connectionInfo, @NonNull Subscription subscription) {
        return new ConnectingState(connectionInfo, subscription);
    }

    @Getter
    private final @NonNull ConnectionInfo connectionInfo;

    @Getter
    private final @NonNull Subscription subscription;

    @Override
    public @NonNull Optional<ConnectingState> asConnectingState() {
        return Optional.of(this);
    }

    public @NonNull ConnectedState toConnected(@NonNull TwitchIO twitchIO, @NonNull String userId) {
        return ConnectedState.create(
                connectionInfo,
                twitchIO,
                userId,
                subscription
        );

    }
}
