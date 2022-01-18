package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

/**
 * Agglomeration of an user id and information of a channel
 */
@Value
public class UserOnChannel {

    @NonNull
    public static UserOnChannel from(@NonNull ExecutionContext executionContext) {
        return from(executionContext.getMessageOwner(),executionContext.getChannelId());
    }

    public static UserOnChannel from(@NonNull MessageContext messageContext) {
        return from(messageContext.getMessageOwner(), messageContext.getChannelId());
    }

    public static UserOnChannel from(@NonNull ChatUser chatUser, @NonNull String channelName) {
        return new UserOnChannel(chatUser.getUserId(), chatUser.getPlatform(), channelName);
    }

    @NonNull String userId;

    @NonNull Platform platform;

    @NonNull String channelName;


    public @NonNull UserOnChannel withUserId(@NonNull String userId) {
        return new UserOnChannel(userId,platform,channelName);
    }
}
