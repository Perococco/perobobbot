package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class SafeClient {
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String clientId;
}
