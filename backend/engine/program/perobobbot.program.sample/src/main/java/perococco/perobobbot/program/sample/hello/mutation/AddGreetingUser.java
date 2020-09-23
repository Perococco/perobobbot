package perococco.perobobbot.program.sample.hello.mutation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.SetTool;
import perobobbot.common.lang.User;
import perobobbot.program.core.ChannelInfo;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.sample.hello.ChannelGreetings;
import perococco.perobobbot.program.sample.hello.HelloState;
import perococco.perobobbot.program.sample.hello.UserOnChannel;

/**
 * Mutation to add a greeting execution to the hello state
 */
@RequiredArgsConstructor
public class AddGreetingUser implements Mutation<HelloState> {

    @NonNull
    public static AddGreetingUser with(@NonNull ExecutionContext executionContext) {
        return new AddGreetingUser(executionContext);
    }

    @NonNull
    private final ExecutionContext executionContext;

    @Override
    public @NonNull HelloState mutate(@NonNull HelloState current) {
        final User user = executionContext.getExecutingUser();
        final UserOnChannel userOnChannel = UserOnChannel.from(executionContext);
        final ChannelInfo channelInfo = executionContext.getChannelInfo();

        if (current.getAlreadyGreeted().contains(userOnChannel)) {
            return current;
        }

        final ImmutableSet<UserOnChannel> newAlreadyGreeted;
        final ImmutableMap<ChannelInfo, ChannelGreetings> newGreetings;

        newAlreadyGreeted = SetTool.add(current.getAlreadyGreeted(), userOnChannel);

        newGreetings = MapTool.add(current.getGreetingsPerChannel(),
                                   channelInfo,
                                   () -> initialChannelGreetings(executionContext),
                                   g -> addOneUser(g, user));

        return new HelloState(newAlreadyGreeted, newGreetings);
    }

    @NonNull
    private ChannelGreetings initialChannelGreetings(@NonNull ExecutionContext executionContext) {
        return new ChannelGreetings(executionContext, ImmutableSet.of(executionContext.getExecutingUser()));
    }

    @NonNull
    private ChannelGreetings addOneUser(@NonNull ChannelGreetings current, @NonNull User user) {
        final ImmutableSet<User> newUsers = SetTool.add(current.getGreeters(), user);
        if (newUsers == current.getGreeters()) {
            return current;
        }
        return new ChannelGreetings(current.getIo(), newUsers);
    }
}
