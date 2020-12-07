package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Mutation;
import perobobbot.lang.Subscription;
import perobobbot.twitch.chat.TwitchChatAlreadyConnected;
import perococco.perobobbot.twitch.chat.state.ConnectingState;
import perococco.perobobbot.twitch.chat.state.ConnectionIdentity;
import perococco.perobobbot.twitch.chat.state.ConnectionState;
import perococco.perobobbot.twitch.chat.state.IdentityMutator;
import perococco.perobobbot.twitch.chat.state.visitor.DisconnectedPredicate;

@RequiredArgsConstructor
public class SetConnecting implements Mutation<ConnectionState> {

    private final @NonNull Subscription subscription;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        if (!DisconnectedPredicate.create().test(currentValue)) {
            throw new TwitchChatAlreadyConnected();
        }
        return ConnectingState.create(subscription);
    }
}
