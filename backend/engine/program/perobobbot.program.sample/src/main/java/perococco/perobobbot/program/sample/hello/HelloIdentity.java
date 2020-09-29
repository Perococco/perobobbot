package perococco.perobobbot.program.sample.hello;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.AsyncIdentity;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.User;
import perococco.perobobbot.program.sample.hello.mutation.AddGreeter;

public class HelloIdentity {

    @NonNull
    @Getter
    private final AsyncIdentity<HelloState> state = AsyncIdentity.create(HelloState.empty());

    public void greetUser(@NonNull ChannelInfo channelInfo, @NonNull User user) {
        state.mutate(new AddGreeter(channelInfo,user));
    }
}
