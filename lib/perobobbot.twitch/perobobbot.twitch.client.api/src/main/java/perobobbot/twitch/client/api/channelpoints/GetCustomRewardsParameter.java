package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.QueryParameterBuilder;

import java.util.Collection;
import java.util.Map;

@Value
public class GetCustomRewardsParameter {

    @NonNull String[] ids;
    @NonNull boolean onlyManageableRewards;

}
