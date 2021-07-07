package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
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
}
