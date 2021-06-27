package perobobbot.twitch.eventsub.api.subscription;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public interface SubscriptionFactory {

    @NonNull Subscription create(@NonNull ImmutableMap<String, String> condition);

}
