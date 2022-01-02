package perobobbot.lang.token;

import lombok.NonNull;

import java.time.Instant;

public interface Token<T> {

    /**
     * @return the access token (the actual value of this token)
     */
    @NonNull T getAccessToken();

    /**
     * @return the instant at which the token will expire
     */
    @NonNull Instant getExpirationInstant();

    /**
     * @return the initial duration of the token, {@link #getExpirationInstant()} should be used
     * to determine if the token has expired
     */
    long getDuration();

}
