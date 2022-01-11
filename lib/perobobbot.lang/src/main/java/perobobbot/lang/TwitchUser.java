package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class TwitchUser implements PlatformUser {

    @NonNull UUID id;
    @NonNull String userId;
    @NonNull String login;
    @NonNull String pseudo;


    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }
}
