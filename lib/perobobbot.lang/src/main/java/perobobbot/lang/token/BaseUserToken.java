package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;

import java.time.Instant;

@Value
@NonFinal
@SuperBuilder
public class BaseUserToken<T> implements UserToken<T> {

    @NonNull T accessToken;
    @NonNull T refreshToken;
    @NonNull Instant expirationInstant;
    long duration;
    @NonNull ImmutableSet<? extends Scope> scopes;

}