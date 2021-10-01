package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;

public interface PredicationEvent extends BroadcasterProvider, EventSubEvent {
    @NonNull String getId();
    @NonNull UserInfo getBroadcaster();
    @NonNull String getTitle();
    @NonNull ImmutableList<Outcome> getOutcomes();
    @NonNull Instant getStartedAt();
}
