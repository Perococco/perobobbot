package perococco.perobobbot.program.sample.hello;

import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.MessageContext;
import perobobbot.program.core.ExecutionContext;

@Value
public class UserOnChannel {

    @NonNull
    public static UserOnChannel from(@NonNull ExecutionContext executionContext) {
        return new UserOnChannel(executionContext.getMessageOwnerId(), executionContext.getChannelInfo());
    }

    public static UserOnChannel from(@NonNull MessageContext messageContext) {
        return new UserOnChannel(messageContext.getMessageOwnerId(), messageContext.getChannelInfo());
    }

    @NonNull String userId;

    @NonNull ChannelInfo channelId;

}
