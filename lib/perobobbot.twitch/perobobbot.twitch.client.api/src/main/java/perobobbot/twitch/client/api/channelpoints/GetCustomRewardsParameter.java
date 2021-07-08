package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import lombok.Value;

@Value
public class GetCustomRewardsParameter {

    @NonNull String[] ids;
    @NonNull boolean onlyManageableRewards;

}
