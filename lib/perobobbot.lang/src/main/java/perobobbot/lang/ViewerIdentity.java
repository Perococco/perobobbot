package perobobbot.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@TypeScript
public class ViewerIdentity {
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String viewerId;
    @NonNull String pseudo;

}
