package perobobbot.twitch.eventsub.api.condition;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChannelPredicationProgress extends SingleConditionSubscription{

    @NonNull String broadcasterId;

    public ChannelPredicationProgress(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_PREDICTION_PROGRESS,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
