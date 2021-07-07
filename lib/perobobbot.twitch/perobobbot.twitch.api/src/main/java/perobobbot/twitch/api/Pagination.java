package perobobbot.twitch.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Pagination {
    @NonNull String cursor;
}
