package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class Product {
    @NonNull String name;
    int bits;
    @NonNull String sku;
    boolean inDevelopment;
}
