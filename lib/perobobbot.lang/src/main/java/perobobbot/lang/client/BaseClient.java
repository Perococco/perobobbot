package perobobbot.lang.client;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import perobobbot.lang.Platform;

import java.util.UUID;

@Value
@NonFinal
@EqualsAndHashCode(of = "id")
abstract class BaseClient<T> implements Client<T> {

    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull T clientSecret;

    public @NonNull SafeClient stripSecret() {
        return new SafeClient(id,platform,clientId);
    }

}
