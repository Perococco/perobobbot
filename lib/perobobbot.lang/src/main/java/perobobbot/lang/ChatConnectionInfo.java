package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(of = {"botId","platform"})
public class ChatConnectionInfo {

    @NonNull UUID botId;

    @NonNull String botName;

    @NonNull Platform platform;

    @NonNull Credential credential;

    public @NonNull String getNick() {
        return credential.getNick();
    }
}
