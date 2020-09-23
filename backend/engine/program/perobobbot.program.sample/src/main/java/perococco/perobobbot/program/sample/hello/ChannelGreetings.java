package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.ExecutionIO;

/**
 * All the users that perform a greeting (Hello, Hi, ...) on
 * a specific channel. Greetings can be sent back to the channel
 * with the {@link ExecutionIO} as member of the class.
 */
@RequiredArgsConstructor
@Getter
public class ChannelGreetings implements ExecutionIO{

    @NonNull
    private final ExecutionIO io;

    @NonNull
    private final ImmutableSet<User> greeters;

    public boolean contains(User user) {
        return greeters.contains(user);
    }

    public boolean hasNoGreeters() {
        return greeters.isEmpty();
    }

    @Override
    public void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        io.print(messageBuilder);
    }

    @Override
    public void print(@NonNull String message) {
        io.print(message);
    }
}
