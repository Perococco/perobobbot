package perococco.perobobbot.twitch.chat.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Subscription;

@RequiredArgsConstructor
public class ConnectingState extends NotConnectedState {

    public static @NonNull ConnectingState create(@NonNull Subscription subscription) {
        return new ConnectingState(subscription);
    }

    @Getter
    private final @NonNull Subscription subscription;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
