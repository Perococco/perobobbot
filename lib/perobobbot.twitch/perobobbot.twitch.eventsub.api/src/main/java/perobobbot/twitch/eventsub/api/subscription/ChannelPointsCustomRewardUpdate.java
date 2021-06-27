package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
public class ChannelPointsCustomRewardUpdate implements Subscription {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var broadcasterId = helper.get(CriteriaType.BROADCASTER_USER_ID);
        final var rewardId = helper.find(CriteriaType.REWARD_ID);
        return rewardId.map(r -> new ChannelPointsCustomRewardUpdate(broadcasterId, r))
                       .orElseGet(() -> new ChannelPointsCustomRewardUpdate(broadcasterId));
    };


    @NonNull String broadcasterId;
    String rewardId;

    public ChannelPointsCustomRewardUpdate(@NonNull String broadcasterId) {
        this.broadcasterId = broadcasterId;
        this.rewardId = null;
    }

    public ChannelPointsCustomRewardUpdate(@NonNull String broadcasterId, @NonNull String rewardId) {
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
