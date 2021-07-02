package perobobbot.twitch.eventsub.api.event;

import lombok.Value;

@Value
public class GlobalCooldown {
    boolean enabled;
    int seconds;
}
