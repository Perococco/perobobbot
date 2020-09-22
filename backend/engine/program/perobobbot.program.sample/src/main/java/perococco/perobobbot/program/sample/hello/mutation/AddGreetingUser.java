package perococco.perobobbot.program.sample.hello.mutation;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.SetTool;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.sample.hello.GreetingIssuer;
import perococco.perobobbot.program.sample.hello.HelloState;

@RequiredArgsConstructor
public class AddGreetingUser implements Mutation<HelloState> {

    @NonNull
    public static AddGreetingUser with(@NonNull GreetingIssuer greetingIssuer) {
        return new AddGreetingUser(greetingIssuer);
    }

    @NonNull
    public static AddGreetingUser with(@NonNull ExecutionContext executionContext) {
        return with(new GreetingIssuer(
                executionContext.getExecutingUser(),
                executionContext.getChannelId(),
                executionContext
        ));
    }

    @NonNull
    private final GreetingIssuer greetingIssuer;

    @Override
    public @NonNull HelloState mutate(@NonNull HelloState state) {
        if (state.getAlreadyGreeted().contains(greetingIssuer.getUserId())) {
            return state;
        }
        final ImmutableSet<String> newAlreadyGreetedGreeted = SetTool.add(state.getAlreadyGreeted(), greetingIssuer.getUserId());
        final ImmutableSet<GreetingIssuer> newToGreet = SetTool.add(state.getGreetingIssuers(), greetingIssuer);

        return new HelloState(newAlreadyGreetedGreeted,newToGreet);
    }
}
