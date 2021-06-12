package perobobbot.twutch.eventsub.manager;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.TwitchSubscription;

@Value
public class EventSubVerification {

    @NonNull String challenge;
    @NonNull TwitchSubscription subscription;

}
