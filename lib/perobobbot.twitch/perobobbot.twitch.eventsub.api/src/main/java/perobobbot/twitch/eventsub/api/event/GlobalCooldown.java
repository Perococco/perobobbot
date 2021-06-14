package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

@Value
public class GlobalCooldown {
    boolean enabled;
    int seconds;
}
