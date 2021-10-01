package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.Owned;
import perobobbot.twitch.api.UserInfo;

import java.util.Optional;

public interface BroadcasterProvider extends Owned {

    @NonNull UserInfo getBroadcaster();

    @Override
    default @NonNull Optional<IdentityInfo> getOwner() {
        return Optional.ofNullable(getBroadcaster().asIdentityInfo());
    }
}
