package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import perobobbot.http.Page;
import perobobbot.oauth.UserApiToken;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TwitchServiceChannelPointsWithToken {

    @NonNull Mono<CustomReward> createCustomReward(@NonNull UserApiToken userApiToken, @NonNull CreateCustomRewardParameter parameter);

    @NonNull Mono<?> deleteCustomRewards(@NonNull UserApiToken userApiToken, @NonNull String customRewardId);

    @NonNull Flux<CustomReward> getCustomReward(@NonNull UserApiToken userApiToken, @NonNull GetCustomRewardsParameter parameter);

    @NonNull Mono<Page<CustomRewardRedemption[],GetCustomRewardRedemptionParameter>> getCustomRewardRedemption(@NonNull UserApiToken userApiToken, @NonNull GetCustomRewardRedemptionParameter parameter);

    @NonNull Mono<CustomReward> updateCustomReward(@NonNull UserApiToken userApiTokenl, @NonNull String customRewardId, @NonNull UpdateCustomRewardParameter parameter);

    @NonNull Mono<CustomRewardRedemption[]> updateRedemptionStatus(@NonNull UserApiToken userApiToken, @NonNull String rewardId, @NonNull String[] redemptionsId, @NonNull UpdateRedemptionStatus parameter);
}
