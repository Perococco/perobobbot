package bot.twitch.chat;

import lombok.NonNull;
import lombok.Value;

@Value
public class Badge {

    @NonNull
    public static Badge with(@NonNull String name, int version) {
        return new Badge(name, version);
    }

    @NonNull
    private final String name;

    private final int version;
}
