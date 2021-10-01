package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.eventsub.api.Tier;

import java.util.Optional;
import java.util.OptionalInt;

@Value
public class SubscriptionGiftEvent implements BroadcasterProvider, EventSubEvent {

    UserInfo user;
    @NonNull UserInfo broadcaster;
    int total;
    @NonNull Tier tier;
    Integer cumulativeTotal;
    boolean anonymous;

    public @NonNull OptionalInt getCumulativeTotal() {
        return cumulativeTotal==null ? OptionalInt.empty():OptionalInt.of(cumulativeTotal);
    }

    public @NonNull Optional<UserInfo> getUser() {
        return Optional.ofNullable(user);
    }
}
