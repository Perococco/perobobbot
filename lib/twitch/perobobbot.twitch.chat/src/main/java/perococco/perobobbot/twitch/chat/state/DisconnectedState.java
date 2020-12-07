package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;

public class DisconnectedState extends NotConnectedState {

    public static @NonNull DisconnectedState create() {
        return DISCONNECTED;
    }

    private static final DisconnectedState DISCONNECTED = new DisconnectedState();

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
