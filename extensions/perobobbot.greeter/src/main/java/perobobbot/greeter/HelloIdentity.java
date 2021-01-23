package perobobbot.greeter;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.greeter.mutation.AddGreeter;
import perobobbot.lang.*;
import perobobbot.lang.fp.Value3;

import java.util.concurrent.CompletionStage;

public class HelloIdentity {

    @NonNull
    @Getter
    private final AsyncIdentity<HelloState> identity = AsyncIdentity.create(HelloState.empty());

    public void greetUser(@NonNull Value3<ChatConnectionInfo,ChatUser,ChannelInfo> greetingInfo) {
        identity.mutate(new AddGreeter(greetingInfo));
    }

    @NonNull
    public CompletionStage<HelloState> getRootState() {
        return identity.getRootState();
    }

    public @NonNull <T> CompletionStage<T> mutateAndGetFromOldState(@NonNull Mutation<HelloState> mutation, @NonNull GetterOnOldState<? super HelloState, ? extends T> getter) {
        return identity.operate(mutation.thenGet(getter));
    }
}
