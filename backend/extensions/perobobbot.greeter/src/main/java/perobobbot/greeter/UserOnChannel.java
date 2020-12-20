package perobobbot.greeter;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MessageContext;

/**
 * Agglomeration of an user id and information of a channel
 */
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
