package perococco.perobobbot.program.sample.hello.mutation;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.common.lang.Mutation;
import perococco.perobobbot.program.sample.hello.HelloState;

public class ClearGreetingIssuers implements Mutation<HelloState> {

    public static ClearGreetingIssuers create() {
        return new ClearGreetingIssuers();
    }

    @Override
    public @NonNull HelloState mutate(@NonNull HelloState state) {
        if (state.getGreetersPerChannel().isEmpty()) {
            return state;
        }
        return new HelloState(state.getAlreadyGreeted(), ImmutableMap.of());
    }
}
