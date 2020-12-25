package perobobbot.greeter.mutation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.greeter.HelloState;
import perobobbot.greeter.UserOnChannel;
import perobobbot.lang.*;

/**
 * Mutation to add a greeting execution to the hello state
 */
@RequiredArgsConstructor
public class AddGreeter implements Mutation<HelloState> {

    @NonNull
    public static AddGreeter with(@NonNull ExecutionContext executionContext) {
        return new AddGreeter(executionContext.getChannelInfo(),executionContext.getMessageOwner());
    }

    @NonNull
    private final ChannelInfo channelInfo;

    @NonNull
    private final User greeter;


    @Override
    public @NonNull HelloState mutate(@NonNull HelloState current) {
        final UserOnChannel userOnChannel = new UserOnChannel(greeter.getUserId(), channelInfo);

        if (current.getAlreadyGreeted().contains(userOnChannel)) {
            return current;
        }

        final ImmutableSet<UserOnChannel> newAlreadyGreeted;
        final ImmutableMap<ChannelInfo, ImmutableSet<User>> newGreeters;

        newAlreadyGreeted = SetTool.add(current.getAlreadyGreeted(), userOnChannel);

        newGreeters = MapTool.add(current.getGreetersPerChannel(),
                                  channelInfo,
                                  () -> ImmutableSet.of(greeter),
                                   set -> SetTool.add(set, greeter));

        return new HelloState(newAlreadyGreeted, newGreeters);
    }
}
