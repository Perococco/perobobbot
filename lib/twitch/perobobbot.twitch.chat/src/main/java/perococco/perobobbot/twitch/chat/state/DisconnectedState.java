package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Subscription;

import java.util.Optional;

@RequiredArgsConstructor
public class DisconnectedState extends NotConnectedState {

    public static @NonNull DisconnectedState create(@NonNull ChatConnectionInfo chatConnectionInfo) {
        return new DisconnectedState(chatConnectionInfo);
    }

    @Getter
    private final @NonNull ChatConnectionInfo chatConnectionInfo;

    @Override
    public @NonNull Optional<DisconnectedState> asDisconnectedState() {
        return Optional.of(this);
    }

    public @NonNull ConnectionState toConnecting(Subscription subscription) {
        return ConnectingState.create(chatConnectionInfo, subscription);
    }
}
