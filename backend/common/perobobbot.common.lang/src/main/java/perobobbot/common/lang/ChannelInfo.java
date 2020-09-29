package perobobbot.common.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelInfo {

    @NonNull
    Platform platform;

    @NonNull
    String channelName;

    public boolean isOwnedBy(User user) {
        if (platform == Platform.TWITCH) {
            return channelName.equals(user.getUserId());
        } else {
            return false;
        }
    }
}
