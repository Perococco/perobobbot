package perobobbot.twitch.eventsub.api.event;

import lombok.Value;

@Value
public class Emote {
    int begin;
    int end;
    String id;
}
