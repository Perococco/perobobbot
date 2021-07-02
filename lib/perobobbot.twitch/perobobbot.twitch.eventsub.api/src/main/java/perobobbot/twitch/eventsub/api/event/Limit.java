package perobobbot.twitch.eventsub.api.event;

import lombok.Value;

@Value
public class Limit {
    boolean enabled;
    int value;
}
