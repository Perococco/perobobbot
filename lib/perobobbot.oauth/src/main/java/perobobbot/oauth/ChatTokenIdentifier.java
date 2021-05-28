package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;

import java.util.UUID;

@Value
public class ChatTokenIdentifier implements TokenIdentifier{

    @NonNull UUID botId;
    @NonNull String viewerId;
    @NonNull Platform platform;
    @NonNull String channelName;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
