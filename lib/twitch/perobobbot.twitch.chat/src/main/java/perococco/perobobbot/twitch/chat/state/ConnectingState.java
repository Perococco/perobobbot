package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Subscription;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.Optional;

@RequiredArgsConstructor
public class ConnectingState extends NotConnectedState {

    public static @NonNull ConnectingState create(@NonNull ChatConnectionInfo chatConnectionInfo, @NonNull Subscription subscription) {
        return new ConnectingState(chatConnectionInfo, subscription);
    }

    @Getter
    private final @NonNull ChatConnectionInfo chatConnectionInfo;

    @Getter
    private final @NonNull Subscription subscription;

    @Override
    public @NonNull Optional<ConnectingState> asConnectingState() {
        return Optional.of(this);
    }

    public @NonNull ConnectedState toConnected(@NonNull TwitchIO twitchIO, @NonNull String userId) {
        return ConnectedState.create(
                chatConnectionInfo,
                twitchIO,
                userId,
                subscription
        );

    }
}
