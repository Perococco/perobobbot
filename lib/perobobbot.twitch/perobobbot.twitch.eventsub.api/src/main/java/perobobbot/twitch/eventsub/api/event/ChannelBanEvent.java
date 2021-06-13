package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class ChannelBanEvent implements EventSubEvent {
    @NonNull UserInfo user;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo moderator;
    @NonNull String reason;
    @Getter(AccessLevel.NONE)
    @JsonAlias("ends_at")
    String endsAt;
    @JsonAlias("is_permanent")
    boolean permanent;

    public @NonNull Optional<String> getEndsAt() {
        return Optional.ofNullable(endsAt);
    }
}
