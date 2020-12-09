package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import perobobbot.lang.Mutation;
import perobobbot.twitch.chat.TwicthChatNotConnected;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

public interface ConnectedStateMutation extends Mutation<ConnectionState> {

    @Override
    @NonNull
    default ConnectionState mutate(@NonNull ConnectionState currentValue) {
        final ConnectedState connectedState = currentValue.asConnectedState()
                                                          .orElseThrow(TwicthChatNotConnected::new);
        return mutate(connectedState);
    }

    @NonNull
    ConnectionState mutate(@NonNull ConnectedState currentValue);

}
