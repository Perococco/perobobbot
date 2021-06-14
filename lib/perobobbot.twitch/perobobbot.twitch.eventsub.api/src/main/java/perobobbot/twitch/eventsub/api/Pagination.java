package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Pagination {
    @NonNull String cursor;
}
