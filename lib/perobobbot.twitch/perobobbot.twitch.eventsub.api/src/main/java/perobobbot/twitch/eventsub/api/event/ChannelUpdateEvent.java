package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelUpdateEvent implements EventSubEvent {

    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String language;
    @NonNull String categoryId;
    @NonNull String category_name;
    boolean mature;
}
