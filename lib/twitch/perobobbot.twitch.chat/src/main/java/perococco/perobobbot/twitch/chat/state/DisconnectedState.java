package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.lang.Subscription;

import java.util.Optional;

public class DisconnectedState extends NotConnectedState {

    public static @NonNull DisconnectedState create() {
        return DISCONNECTED;
    }

    private static final DisconnectedState DISCONNECTED = new DisconnectedState();

    @Override
    public @NonNull Optional<DisconnectedState> asDisconnectedState() {
        return Optional.of(this);
    }

    public @NonNull ConnectionState toConnecting(Subscription subscription) {
        return ConnectingState.create(subscription);
    }
}
