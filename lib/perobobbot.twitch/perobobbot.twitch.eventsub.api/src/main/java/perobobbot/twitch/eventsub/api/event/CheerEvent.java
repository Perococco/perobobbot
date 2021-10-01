package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
public class CheerEvent implements BroadcasterProvider, EventSubEvent {
    UserInfo user;
    @NonNull UserInfo broadcaster;
    boolean anonymous;
    @NonNull String message;
    int bits;

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
