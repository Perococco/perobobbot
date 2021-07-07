package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class TopPredictor {
    @NonNull UserInfo user;
    int channelPointsWon;
    int channelPointsUsed;
}
