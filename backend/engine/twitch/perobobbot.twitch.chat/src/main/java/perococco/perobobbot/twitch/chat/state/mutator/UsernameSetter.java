package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.twitch.chat.state.ConnectionValue;
import perococco.perobobbot.twitch.chat.state.IdentityMutator;

@RequiredArgsConstructor
public class UsernameSetter implements IdentityMutator {

    @NonNull
    private final String username;

    @Override
    public @NonNull ConnectionValue mutate(@NonNull ConnectionValue currentValue) {
        return currentValue.toBuilder().userName(username).build();
    }
}
