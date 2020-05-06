package perococco.bot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.state.ConnectionValue;
import perococco.bot.twitch.chat.state.IdentityMutator;

@RequiredArgsConstructor
public class UsernameSetter implements IdentityMutator {

    @NonNull
    private final String username;

    @Override
    public @NonNull ConnectionValue mutate(@NonNull ConnectionValue currentValue) {
        return currentValue.toBuilder().userName(username).build();
    }
}
