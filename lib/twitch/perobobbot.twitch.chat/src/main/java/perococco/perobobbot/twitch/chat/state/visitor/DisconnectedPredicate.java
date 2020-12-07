package perococco.perobobbot.twitch.chat.state.visitor;

import lombok.NonNull;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectingState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;
import perococco.perobobbot.twitch.chat.state.DisconnectedState;

import java.util.function.Predicate;

public class DisconnectedPredicate implements ConnectionState.Visitor<Boolean>, Predicate<ConnectionState> {

    public static @NonNull DisconnectedPredicate create() {
        return new DisconnectedPredicate();
    }

    @Override
    public boolean test(ConnectionState connectionState) {
        return connectionState.accept(this);
    }

    @Override
    public @NonNull Boolean visit(@NonNull DisconnectedState state) {
        return true;
    }

    @Override
    public @NonNull Boolean visit(@NonNull ConnectingState state) {
        return false;
    }

    @Override
    public @NonNull Boolean visit(@NonNull ConnectedState state) {
        return false;
    }
}
