package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class CheerEvent implements EventSubEvent {
    UserInfo user;
    @NonNull UserInfo broadcaster;
    boolean anonymous;
    @NonNull String message;
    int bits;

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
