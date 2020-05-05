package bot.twitch.chat;

import lombok.NonNull;
import lombok.Value;

@Value
public class Badge {

    @NonNull
    private final String name;

    private final int version;
}
