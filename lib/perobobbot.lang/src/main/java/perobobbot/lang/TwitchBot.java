package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class TwitchBot implements PlatformBot{

    @NonNull UUID id;
    @NonNull Bot bot;
    @NonNull TwitchUser user;
}
