package perobobbot.lang.token;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;
import java.util.Optional;

@Value
@NonFinal
public class BaseClientToken<T> implements ClientToken<T> {

    @NonNull T accessToken;
    @NonNull Instant expirationInstant;
    long duration;



}
