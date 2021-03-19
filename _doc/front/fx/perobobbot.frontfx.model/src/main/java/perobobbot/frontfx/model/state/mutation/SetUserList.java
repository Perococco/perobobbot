package perobobbot.frontfx.model.state.mutation;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.DataState;
import perobobbot.lang.Mutation;
import perobobbot.security.com.SimpleUser;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SetUserList implements Mutation<DataState> {

    public static @NonNull SetUserList with(@NonNull ImmutableList<SimpleUser> users) {
        return new SetUserList(users);
    }


    private final @NonNull ImmutableList<SimpleUser> users;

    @Override
    public @NonNull DataState mutate(@NonNull DataState state) {
        return state.withUsers(users);
    }
}
