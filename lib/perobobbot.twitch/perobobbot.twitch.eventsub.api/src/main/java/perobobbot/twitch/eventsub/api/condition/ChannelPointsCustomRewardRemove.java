package perobobbot.twitch.eventsub.api.condition;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
public class ChannelPointsCustomRewardRemove implements Subscription {

    @NonNull String broadcasterId;
    String rewardId;

    public ChannelPointsCustomRewardRemove(@NonNull String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardRemove(@NonNull String broadcasterId, @NonNull String rewardId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = rewardId;
    }

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE;
    }

    @Override
    public @NonNull ImmutableMap<CriteriaType, String> getCondition() {
        final var builder = ImmutableMap.<CriteriaType,String>builder();

        builder.put(CriteriaType.BROADCASTER_USER_ID,broadcasterId);
        if (rewardId != null) {
            builder.put(CriteriaType.REWARD_ID,rewardId);
        }
        return builder.build();
    }
}
