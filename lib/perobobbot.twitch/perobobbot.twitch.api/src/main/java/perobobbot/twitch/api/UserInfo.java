package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.Owned;
import perobobbot.lang.Platform;

@Value
public class UserInfo {

    @NonNull String id;
    @NonNull String login;
    @NonNull String name;

    public IdentityInfo asIdentityInfo() {
        return IdentityInfo.builder()
                           .getPlatform(Platform.TWITCH)
                           .getPseudo(name)
                           .getLogin(login)
                           .getViewerId(id)
                           .build();
    }
}
