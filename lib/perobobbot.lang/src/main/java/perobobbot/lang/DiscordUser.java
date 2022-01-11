package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class DiscordUser implements PlatformUser {

    @NonNull UUID id;
    @NonNull String userId;
    @NonNull String login;
    @NonNull String discriminator;

    @Override
    public @NonNull String getPseudo() {
        return login;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.DISCORD;
    }
}

