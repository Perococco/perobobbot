package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;

import java.util.UUID;

@Value
public class ChatTokenIdentifier implements TokenIdentifier {

    /**
     * The id of the {@link perobobbot.lang.Bot} on which the command has been executed
     */
    @NonNull UUID botId;
    /**
     * The platform on which the command has been executed
     */
    @NonNull Platform platform;
    /**
     * The id of the user (platform dependent) that executed the command
     */
    @NonNull String userId;
    /**
     * The id of the channel (platform dependent, it's name for Twitch for instance) on which the command has been executed
     */
    @NonNull String channelId;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
