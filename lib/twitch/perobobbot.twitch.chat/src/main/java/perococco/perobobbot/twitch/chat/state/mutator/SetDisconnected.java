package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import perobobbot.lang.Mutation;
import perobobbot.lang.Subscription;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectingState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

public class SetDisconnected implements Mutation<ConnectionState> {

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        retrieveSubscription(currentValue).unsubscribe();
        return ConnectionState.disconnected(currentValue.getBot());
    }

    private @NonNull Subscription retrieveSubscription(@NonNull ConnectionState connectionState) {
        return connectionState.asConnectedState()
                              .map(ConnectedState::getSubscription)
                              .or(() -> connectionState.asConnectingState()
                                                       .map(ConnectingState::getSubscription))
                              .orElse(Subscription.NONE);
    }

}
