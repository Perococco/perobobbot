package perobobbot.lang;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Value
@NonFinal
@SuperBuilder
@EqualsAndHashCode(of = "id")
public class BaseClient<T> {

    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String clientId;
    @NonNull T clientSecret;

    public @NonNull SafeClient stripSecret() {
        return new SafeClient(id,platform,clientId);
    }

}
