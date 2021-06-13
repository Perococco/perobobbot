package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelUpdateEvent implements EventSubEvent {

    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull String language;
    @JsonAlias("category_id")
    @NonNull String categoryId;
    @JsonAlias("category_name")
    @NonNull String category_name;
    @JsonAlias("is_mature")
    boolean mature;
}
