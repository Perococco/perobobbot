package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class ConnectionInfo {

    @NonNull UUID botId;

    @NonNull String botName;

    @NonNull Platform platform;

    @NonNull Credential credential;

    public @NonNull String getNick() {
        return credential.getNick();
    }
}
