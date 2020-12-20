package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bot;
import perobobbot.lang.Subscription;

import java.util.Optional;

@RequiredArgsConstructor
public class DisconnectedState extends NotConnectedState {

    public static @NonNull DisconnectedState create(@NonNull Bot bot) {
        return new DisconnectedState(bot);
    }

    @Getter
    private final @NonNull Bot bot;

    @Override
    public @NonNull Optional<DisconnectedState> asDisconnectedState() {
        return Optional.of(this);
    }

    public @NonNull ConnectionState toConnecting(Subscription subscription) {
        return ConnectingState.create(bot, subscription);
    }
}
