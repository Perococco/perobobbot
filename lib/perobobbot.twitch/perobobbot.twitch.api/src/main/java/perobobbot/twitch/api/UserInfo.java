package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.TwitchIdentity;

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

    public TwitchIdentity asUserIdentity() {
        return new TwitchIdentity(id,login,name);
    }
}

