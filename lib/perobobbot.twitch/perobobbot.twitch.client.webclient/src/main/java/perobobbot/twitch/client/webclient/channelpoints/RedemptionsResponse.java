package perobobbot.twitch.client.webclient.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.http.Page;
import perobobbot.twitch.api.Pagination;
import perobobbot.twitch.client.api.channelpoints.CustomRewardRedemption;
import perobobbot.twitch.client.api.channelpoints.GetCustomRewardRedemptionParameter;

@Value
public class RedemptionsResponse {

    @NonNull CustomRewardRedemption[] data;
    Pagination pagination;

    public @NonNull Page<CustomRewardRedemption[],GetCustomRewardRedemptionParameter> createPage(@NonNull GetCustomRewardRedemptionParameter parameter) {
        if (pagination == null) {
            return Page.withoutNext(data);
        } else {
            return Page.withNext(data,parameter.createNextPage(pagination));
        }
    }
}
