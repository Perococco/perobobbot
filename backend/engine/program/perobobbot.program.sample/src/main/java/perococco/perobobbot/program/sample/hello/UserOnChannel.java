package perococco.perobobbot.program.sample.hello;

import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.program.core.ExecutionContext;

@Value
public class UserOnChannel {

    @NonNull
    public static UserOnChannel from(@NonNull ExecutionContext executionContext) {
        return new UserOnChannel(executionContext.getMessageOwnerId(), executionContext.getChannelInfo());
    }

    @NonNull String userId;

    @NonNull ChannelInfo channelId;

}
