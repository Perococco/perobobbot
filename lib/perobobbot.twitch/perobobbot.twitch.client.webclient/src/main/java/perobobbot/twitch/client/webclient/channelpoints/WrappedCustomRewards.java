package perobobbot.twitch.client.webclient.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.channelpoints.CustomReward;

@Value
public class WrappedCustomRewards {

    @NonNull CustomReward[] data;

}
