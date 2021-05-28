package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

@Value
public class CreateClientParameter {
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull Secret clientSecret;
}
