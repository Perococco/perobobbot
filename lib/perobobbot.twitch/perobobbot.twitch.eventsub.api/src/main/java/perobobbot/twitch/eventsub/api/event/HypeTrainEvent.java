package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;

public interface HypeTrainEvent extends EventSubEvent {

    @NonNull String getId();
    @NonNull UserInfo getBroadcaster();
    int getTotal();
}
