package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import perobobbot.lang.Operator;
import perococco.perobobbot.twitch.chat.TwitchIO;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

public interface OperatorUsingIO<T> extends Operator<ConnectionState,T> {

    @Override
    @NonNull
    default ConnectionState mutate(ConnectionState state) {
        return state;
    }
    @Override
    @NonNull
    default T apply(@NonNull ConnectionState state) {
        return withIO(state.getTwitchIO());
    }

    T withIO(@NonNull TwitchIO io);
}
