package perobobbot.frontfx.model.state.mutation;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.lang.Mutation;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClearAuthenticatedUser implements Mutation<ApplicationState> {

    private static final ClearAuthenticatedUser INSTANCE = new ClearAuthenticatedUser();

    public static @NonNull ClearAuthenticatedUser create() {
        return INSTANCE;
    }

    @Override
    public @NonNull ApplicationState mutate(@NonNull ApplicationState state) {
        return state.toBuilder().user(null).build();
    }
}
