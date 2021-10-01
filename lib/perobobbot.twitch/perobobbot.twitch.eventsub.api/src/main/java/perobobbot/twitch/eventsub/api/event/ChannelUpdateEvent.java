package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ChannelUpdateEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String language;
    @NonNull String categoryId;
    @NonNull String category_name;
    boolean mature;
}
