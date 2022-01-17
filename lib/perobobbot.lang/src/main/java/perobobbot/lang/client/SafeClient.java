package perobobbot.lang.client;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.TypeScript;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class SafeClient {
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String clientId;
}
