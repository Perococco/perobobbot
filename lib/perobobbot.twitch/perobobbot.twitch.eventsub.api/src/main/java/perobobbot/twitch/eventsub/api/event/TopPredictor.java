package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class TopPredictor {
    @NonNull UserInfo user;
    int channelPointsWon;
    int channelPointsUsed;
}
