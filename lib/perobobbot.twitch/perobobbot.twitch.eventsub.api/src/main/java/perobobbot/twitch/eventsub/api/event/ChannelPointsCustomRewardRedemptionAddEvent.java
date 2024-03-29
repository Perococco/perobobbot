package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.RewardRedemptionStatus;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

@Value
public class ChannelPointsCustomRewardRedemptionAddEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull String userInput;
    @NonNull RewardRedemptionStatus status;
    @NonNull Reward reward;
    @NonNull Instant redeemedAt;

}
