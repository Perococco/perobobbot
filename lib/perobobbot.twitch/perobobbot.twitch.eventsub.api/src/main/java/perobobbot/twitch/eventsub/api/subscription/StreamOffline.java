package perobobbot.twitch.eventsub.api.subscription;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class StreamOffline extends SingleConditionSubscription {

    @NonNull String broadcasterId;

    public StreamOffline(@NonNull String broadcasterId) {
        super(SubscriptionType.STREAM_OFFLINE,CriteriaType.BROADCASTER_USER_ID);
        this.broadcasterId = broadcasterId;
    }

    @Override
    protected String getConditionValue() {
        return broadcasterId;
    }
}
