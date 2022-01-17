package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@Value
@TypeScript
public class CreateClientParameter {
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull Secret clientSecret;
}
