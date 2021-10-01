package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentityInfo;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
public class UserUpdateEvent implements EventSubEvent {
    @NonNull UserInfo user;
    String email;
    @NonNull String description;

    public @NonNull Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    @Override
    public @NonNull Optional<IdentityInfo> getOwner() {
        return Optional.ofNullable(user.asIdentityInfo());
    }
}
