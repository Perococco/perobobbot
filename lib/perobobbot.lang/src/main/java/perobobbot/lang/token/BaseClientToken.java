package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Value
@NonFinal
public class BaseClientToken<T> implements ClientToken<T> {

    @NonNull T accessToken;
    @NonNull Instant expirationInstant;
    long duration;

}
