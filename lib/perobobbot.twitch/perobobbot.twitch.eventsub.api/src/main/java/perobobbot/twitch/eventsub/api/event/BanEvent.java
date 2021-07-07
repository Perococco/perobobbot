package perobobbot.twitch.eventsub.api.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

@Value
public class BanEvent implements EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo moderator;
    @NonNull String reason;
    @Getter(AccessLevel.NONE)
    String endsAt;
    boolean permanent;

    public @NonNull Optional<String> getEndsAt() {
        return Optional.ofNullable(endsAt);
    }
}
