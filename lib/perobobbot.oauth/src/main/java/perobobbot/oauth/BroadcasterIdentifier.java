package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;

@Value
public class BroadcasterIdentifier implements TokenIdentifier {

    @NonNull Platform platform;
    @NonNull String broadcasterId;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
