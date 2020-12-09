package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Mutation;
import perobobbot.lang.Subscription;
import perobobbot.twitch.chat.TwitchChatAlreadyConnected;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class SetConnecting implements Mutation<ConnectionState> {

    private final @NonNull Subscription subscription;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        return currentValue.asDisconnectedState()
                    .orElseThrow(TwitchChatAlreadyConnected::new)
                    .toConnecting(subscription);
    }
}
