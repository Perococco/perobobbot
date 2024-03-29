package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

/**
 * Some information about a channel
 */
@Value
public class ChannelInfo {

    /**
     * The platform the channel belongs to (TWITCH, DISCORD ...)
     */
    @NonNull
    Platform platform;

    /**
     * The name of the channel
     */
    @NonNull
    String channelName;

    public boolean isOwnedBy(ChatUser user) {
        return switch (platform) {
            case TWITCH -> channelName.equals(user.getUserId());
            case LOCAL -> true;
            case DISCORD -> Todo.TODO();
            case SPOTIFY -> Todo.TODO();
        };
    }
}
