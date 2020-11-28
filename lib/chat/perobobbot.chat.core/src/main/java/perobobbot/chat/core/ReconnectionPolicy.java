package perobobbot.chat.core;

import lombok.NonNull;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/**
 * @author perococco
 **/
public interface ReconnectionPolicy {

    /**
     * @param nbAttemptsSoFar the number of attempts made before calling this method (starts from 0 during a reconnection process)
     * @return true if the reconnection should be attempted again
     */
    boolean shouldReconnect(int nbAttemptsSoFar);

    /**
     * @param nextAttemptIndex the next attempts index (starts from 1 during a reconnection process)
     * @return the delay before trying a connection
     */
    @NonNull
    Duration delayBeforeNextAttempt(int nextAttemptIndex);

    @NonNull
    ReconnectionPolicy NO_RECONNECTION = with(i -> false, i -> Duration.ZERO);

    @NonNull
    static ReconnectionPolicy with(@NonNull IntPredicate shouldReconnectPredicate, @NonNull IntFunction<Duration> durationProvider) {
        return new ReconnectionPolicy() {
            @Override
            public boolean shouldReconnect(int nbAttemptsSoFar) {
                return shouldReconnectPredicate.test(nbAttemptsSoFar);
            }

            @Override
            public @NonNull Duration delayBeforeNextAttempt(int nextAttemptIndex) {
                return durationProvider.apply(nextAttemptIndex);
            }
        };
    }

    @NonNull
    static ReconnectionPolicy withMaximalNumberOfAttempts(int maxAttempts, @NonNull IntFunction<Duration> durationGetter) {
        return with(i -> i<maxAttempts, durationGetter);
    }

    @NonNull
    static ReconnectionPolicy withMaximalNumberOfAttemptsAndFixDelay(int maxAttempts, @NonNull Duration fixDuration) {
        return with(i -> i<maxAttempts, i -> fixDuration);
    }
}
