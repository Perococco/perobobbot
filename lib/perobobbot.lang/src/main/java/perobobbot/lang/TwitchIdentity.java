package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class TwitchIdentity implements UserIdentity {

    @NonNull String userId;
    @NonNull String login;
    @NonNull String pseudo;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
