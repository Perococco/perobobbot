package perobobbot.rest.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;
import perobobbot.lang.Platform;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class RestCredentialInfo {

    @NonNull UUID id;
    @NonNull String login;
    @NonNull Platform platform;
    @NonNull String nick;
    @NonNull boolean secretAvailable;
}
