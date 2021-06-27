package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelRaid implements Subscription {

    public static final SubscriptionFactory FACTORY = condition -> {
        final var helper = new ConditionHelper(condition);
        final var from = helper.find(CriteriaType.FROM_BROADCASTER_USER_ID);
        final var to = helper.find(CriteriaType.TO_BROADCASTER_USER_ID);

        if (from.isPresent()) {
            return ChannelRaid.from(from.get());
        }
        if (to.isPresent()) {
            return ChannelRaid.to(to.get());
        }

        throw new IllegalArgumentException("Invalid condition.");
    };



    public static @NonNull ChannelRaid from(@NonNull String broadcasterId) {
        return new ChannelRaid(broadcasterId,null);
    }

    public static @NonNull ChannelRaid to(@NonNull String broadcasterId) {
        return new ChannelRaid(null,broadcasterId);
    }

    String fromBroadcasterId;
    String toBroadcasterId;

    @Override
    public @NonNull SubscriptionType getType() {
        return SubscriptionType.CHANNEL_RAID;
    }

    @Override
    public @NonNull ImmutableMap<CriteriaType, String> getCondition() {
        final var builder = ImmutableMap.<CriteriaType,String>builder();

        if (fromBroadcasterId != null) {
            builder.put(CriteriaType.FROM_BROADCASTER_USER_ID, fromBroadcasterId);
        }
        if (toBroadcasterId != null) {
            builder.put(CriteriaType.FROM_BROADCASTER_USER_ID, toBroadcasterId);
        }
        return builder.build();
    }
}
