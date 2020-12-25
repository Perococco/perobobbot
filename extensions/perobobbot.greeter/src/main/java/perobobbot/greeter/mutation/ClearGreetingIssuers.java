package perobobbot.greeter.mutation;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.greeter.HelloState;
import perobobbot.lang.Mutation;

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
