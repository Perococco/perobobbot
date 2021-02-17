package perobobbot.frontfx.model.state.mutation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.DataState;
import perobobbot.lang.Bot;
import perobobbot.lang.Mutation;
import perobobbot.security.com.SimpleUser;

@RequiredArgsConstructor
public class SetBotList implements Mutation<DataState> {

    private final @NonNull ImmutableList<Bot> bots;

    @Override
    public @NonNull DataState mutate(@NonNull DataState state) {
        return state.withBots(bots);
    }
}
