package perococco.perobobbot.program.sample.hello;

import lombok.NonNull;
import lombok.Value;
import perobobbot.program.core.ChannelInfo;
import perobobbot.program.core.ExecutionContext;

@Value
public class UserOnChannel {

    @NonNull
    public static UserOnChannel from(@NonNull ExecutionContext executionContext) {
        return new UserOnChannel(executionContext.getExecutingUserId(), executionContext.getChannelInfo());
    }

    @NonNull String userId;

    @NonNull ChannelInfo channelId;

}
