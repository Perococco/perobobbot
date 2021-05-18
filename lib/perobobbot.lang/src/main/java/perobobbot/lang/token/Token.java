package perobobbot.lang.token;

import lombok.NonNull;

import java.time.Instant;

public interface Token<T> {

    @NonNull T getAccessToken();
    @NonNull Instant getExpirationInstant();
    long getDuration();

}
