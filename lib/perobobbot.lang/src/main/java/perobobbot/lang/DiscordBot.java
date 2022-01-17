package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class DiscordBot implements PlatformBot {

    @NonNull UUID id;
    @NonNull Bot bot;
    @NonNull DiscordUser user;
}
