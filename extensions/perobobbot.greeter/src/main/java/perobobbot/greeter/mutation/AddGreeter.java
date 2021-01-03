package perobobbot.greeter.mutation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.greeter.HelloState;
import perobobbot.lang.*;
import perobobbot.lang.fp.Value2;
import perobobbot.lang.fp.Value3;

/**
 * Mutation to add a greeting execution to the hello state
 */
@RequiredArgsConstructor
public class AddGreeter implements Mutation<HelloState> {

    @NonNull
    public static AddGreeter with(@NonNull ExecutionContext executionContext) {
        return new AddGreeter(Value3.of(executionContext.getChatConnectionInfo(),executionContext.getMessageOwner(), executionContext.getChannelInfo()));
    }

    @NonNull
    private final Value3<ChatConnectionInfo,ChatUser,ChannelInfo> greetingInfo;

    @Override
    public @NonNull HelloState mutate(@NonNull HelloState current) {
        if (current.getAlreadyGreeted().contains(greetingInfo)) {
            return current;
        }

        final ImmutableSet<Value3<ChatConnectionInfo,ChatUser,ChannelInfo>> newAlreadyGreeted;
        final ImmutableMap<ChannelInfo, ImmutableSet<Value2<ChatConnectionInfo,ChatUser>>> newGreeters;

        newAlreadyGreeted = SetTool.add(current.getAlreadyGreeted(), greetingInfo);

        newGreeters = MapTool.add(current.getGreetersPerChannel(),
                                  greetingInfo.getC(),
                                  () -> ImmutableSet.of(greetingInfo.dropC()),
                                   set -> SetTool.add(set, greetingInfo.dropC()));

        return new HelloState(newAlreadyGreeted, newGreeters);
    }
}
