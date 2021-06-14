package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserAuthorizationRevokeEvent implements EventSubEvent {

    @NonNull String clientId;
    @NonNull RevokableUserInfo user;

}
