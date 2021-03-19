package perobobbot.frontfx.model.state.mutation;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.lang.Mutation;
import perobobbot.security.com.SimpleUser;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SetAuthenticatedUser implements Mutation<ApplicationState> {

    private final @NonNull SimpleUser user;

    public static @NonNull SetAuthenticatedUser create(SimpleUser user) {
        return new SetAuthenticatedUser(user);
    }

    @Override
    public @NonNull ApplicationState mutate(@NonNull ApplicationState state) {
        return state.toBuilder().user(user).build();
    }
}
