package perobobbot.lang.token;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

import java.time.Instant;

@Value
@NonFinal
@SuperBuilder
public class BaseClientToken<T> implements ClientToken<T> {

    @NonNull T accessToken;
    @NonNull Instant expirationInstant;
    long duration;

}
