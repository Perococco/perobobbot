package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ConnectionInfo;
import perobobbot.lang.Subscription;

import java.util.Optional;

@RequiredArgsConstructor
public class DisconnectedState extends NotConnectedState {

    public static @NonNull DisconnectedState create(@NonNull ConnectionInfo connectionInfo) {
        return new DisconnectedState(connectionInfo);
    }

    @Getter
    private final @NonNull ConnectionInfo connectionInfo;

    @Override
    public @NonNull Optional<DisconnectedState> asDisconnectedState() {
        return Optional.of(this);
    }

    public @NonNull ConnectionState toConnecting(Subscription subscription) {
        return ConnectingState.create(connectionInfo, subscription);
    }
}
