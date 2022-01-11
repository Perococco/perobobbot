package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class DiscordIdentity implements UserIdentity {

    @NonNull String userId;
    @NonNull String login;
    @NonNull String discriminator;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.DISCORD;
    }

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
