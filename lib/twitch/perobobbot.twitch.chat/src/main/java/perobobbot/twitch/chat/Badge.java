package perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.Value;

@Value
public class Badge {

    @NonNull
    public static Badge with(@NonNull String name, int version) {
        return new Badge(name, version);
    }

    @NonNull String name;

    int version;
}
