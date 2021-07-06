package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class ChatConnectionInfo {

    @NonNull UUID botId;

    @NonNull UUID viewerIdentityId;

    @NonNull Platform platform;

    @NonNull String botName;

    /**
     * @return the nick to use to connect
     */
    @NonNull String nick;

    @NonNull Secret secret;
}
