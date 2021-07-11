package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import perobobbot.http.Page;
import perobobbot.oauth.UserOAuth;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TwitchServiceChannelPoints {

    @UserOAuth(scope = "channel:manage:redemptions")
    @NonNull Mono<CustomReward> createCustomReward(@NonNull CreateCustomRewardParameter parameter);

    @UserOAuth(scope = "channel:manage:redemptions")
    @NonNull Mono<?> deleteCustomRewards(@NonNull String customRewardId);

    @UserOAuth(scope = "channel:read:redemptions")
    @NonNull Flux<CustomReward> getCustomReward(@NonNull GetCustomRewardsParameter parameter);

    @UserOAuth(scope = "channel:read:redemptions")
    @NonNull Mono<Page<CustomRewardRedemption[],GetCustomRewardRedemptionParameter>> getCustomRewardRedemption(@NonNull GetCustomRewardRedemptionParameter parameter);

    @UserOAuth(scope = "channel:manage:redemptions")
    @NonNull Mono<CustomReward> updateCustomReward(@NonNull String customRewardId, @NonNull UpdateCustomRewardParameter parameter);

    @UserOAuth(scope = "channel:manage:redemptions")
    @NonNull Mono<CustomRewardRedemption[]> updateRedemptionStatus(@NonNull String rewardId, @NonNull String[] redemptionsId, @NonNull UpdateRedemptionStatus parameter);
}
