package perobobbot.twitch.eventsub.api.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class HypeTrainEnd extends SingleConditionSubscription {

    public static final SubscriptionFactory FACTORY = forSingleCondition(CriteriaType.BROADCASTER_USER_ID, HypeTrainEnd::new);

    @NonNull String broadcasterId;

    public HypeTrainEnd(@NonNull String broadcasterId) {
        super(SubscriptionType.CHANNEL_HYPE_TRAIN_END,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
