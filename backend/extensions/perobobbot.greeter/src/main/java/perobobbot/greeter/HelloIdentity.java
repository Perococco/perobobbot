package perobobbot.greeter;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.*;
import perobobbot.greeter.mutation.AddGreeter;

import java.util.concurrent.CompletionStage;

public class HelloIdentity {

    @NonNull
    @Getter
    private final AsyncIdentity<HelloState> identity = AsyncIdentity.create(HelloState.empty());

    public void greetUser(@NonNull ChannelInfo channelInfo, @NonNull User user) {
        identity.mutate(new AddGreeter(channelInfo, user));
    }

    @NonNull
    public HelloState getRootState() {
        return identity.getRootState();
    }

    public @NonNull <T> CompletionStage<T> mutateAndGetFromOldState(@NonNull Mutation<HelloState> mutation, @NonNull GetterOnOldState<? super HelloState, ? extends T> mutatedStateGetter) {
        return identity.mutateAndGetFromOldState(mutation, mutatedStateGetter);
    }
}
