package perobobbot.twitch.client.api;

import lombok.NonNull;
import lombok.Value;

import java.util.*;

@Value
public class GameSearchParameter {

    @NonNull String[] ids;
    @NonNull String[] names;

    public @NonNull Map<String, Collection<?>> createQueryParameters() {

        final Map<String,Collection<?>> map = new HashMap<>();
        if (ids.length > 0) {
            map.put("id", Arrays.asList(ids));
        }
        if (names.length > 0) {
            map.put("name", Arrays.asList(names));
        }
        return map;
    }
}
