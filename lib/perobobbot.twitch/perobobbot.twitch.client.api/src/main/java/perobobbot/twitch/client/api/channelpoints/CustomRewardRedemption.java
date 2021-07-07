package perobobbot.twitch.client.api.channelpoints;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.api.RewardRedemptionStatus;
import perobobbot.twitch.client.api.TwitchApiPayload;

import java.time.Instant;

@Value
public class CustomRewardRedemption implements TwitchApiPayload {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull BasicReward reward;
    @NonNull String userInput;
    @NonNull RewardRedemptionStatus status;
    @NonNull Instant redeemedAt;

}
