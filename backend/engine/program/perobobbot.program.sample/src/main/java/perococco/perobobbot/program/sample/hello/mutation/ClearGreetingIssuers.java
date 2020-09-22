package perococco.perobobbot.program.sample.hello.mutation;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.Mutation;
import perococco.perobobbot.program.sample.hello.HelloState;

public class ClearGreetingIssuers implements Mutation<HelloState> {

    public static ClearGreetingIssuers create() {
        return new ClearGreetingIssuers();
    }

    @Override
    public @NonNull HelloState mutate(@NonNull HelloState state) {
        if (state.getGreetingIssuers().isEmpty()) {
            return state;
        }
        return new HelloState(state.getAlreadyGreeted(), ImmutableSet.of());
    }
}
