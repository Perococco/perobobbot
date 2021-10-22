package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.Platform;

@Value
public class UserInfo {

    @NonNull String id;
    @NonNull String login;
    @NonNull String name;

    public IdentityInfo asIdentityInfo() {
        return IdentityInfo.builder()
                           .platform(Platform.TWITCH)
                           .viewerId(id)
                           .build();
    }
}
