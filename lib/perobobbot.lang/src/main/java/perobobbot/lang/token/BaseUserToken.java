package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import perobobbot.lang.Scope;

import java.time.Instant;

@Value
@NonFinal
public class BaseUserToken<T> implements UserToken<T> {

    @NonNull T accessToken;
    @NonNull T refreshToken;
    @NonNull Instant expirationInstant;
    long duration;
    @NonNull ImmutableSet<? extends Scope> scopes;

}
