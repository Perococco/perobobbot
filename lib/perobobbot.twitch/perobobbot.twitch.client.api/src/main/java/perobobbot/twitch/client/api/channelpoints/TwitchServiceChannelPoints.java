package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import perobobbot.oauth.UserOAuth;
import perobobbot.oauth.UserApiToken;

public interface TwitchServiceChannelPoints {

    @UserOAuth(scope = "channel:manage:redemptions")
    void createCustomRewards(@NonNull UserApiToken userApiToken);

    @UserOAuth(scope = "channel:manage:redemptions")
    void deleteCustomRewards(@NonNull UserApiToken userApiToken);

    @UserOAuth(scope = "channel:read:redemptions")
    void getCustomReward(@NonNull UserApiToken userApiToken);

    @UserOAuth(scope = "channel:read:redemptions")
    void getCustomRewardRedemption(@NonNull UserApiToken userApiToken);

    @UserOAuth(scope = "channel:manage:redemptions")
    void updateCustomReward(@NonNull UserApiToken userApiToken);

    @UserOAuth(scope = "channel:manage:redemptions")
    void updateRedemptionStatus(@NonNull UserApiToken userApiToken);
}
