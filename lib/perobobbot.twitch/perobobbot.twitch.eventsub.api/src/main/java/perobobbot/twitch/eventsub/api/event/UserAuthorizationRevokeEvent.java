package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentityInfo;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
public class UserAuthorizationRevokeEvent implements EventSubEvent {

    @NonNull String clientId;
    @NonNull RevokableUserInfo user;

    @Override
    public @NonNull Optional<IdentityInfo> getOwner() {
        return user.asNotRevoked().map(UserInfo::asIdentityInfo);
    }
}
