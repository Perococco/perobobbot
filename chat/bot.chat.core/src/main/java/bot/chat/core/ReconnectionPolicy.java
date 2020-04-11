package bot.chat.core;

import lombok.NonNull;

import java.time.Duration;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/**
 * @author perococco
 **/
public interface ReconnectionPolicy {


    boolean shouldReconnect(int nbAttemptsSoFar);

    @NonNull
    Duration delayBeforeNextAttempt(int nexAttemptIndex);


    @NonNull
    static ReconnectionPolicy with(@NonNull IntPredicate shouldReconnectPredicate, @NonNull IntFunction<Duration> durationProvider) {
        return new ReconnectionPolicy() {
            @Override
            public boolean shouldReconnect(int nbAttemptsSoFar) {
                return shouldReconnectPredicate.test(nbAttemptsSoFar);
            }

            @Override
            public @NonNull Duration delayBeforeNextAttempt(int nexAttemptIndex) {
                return durationProvider.apply(nexAttemptIndex);
            }
        };
    }

    @NonNull
    static ReconnectionPolicy with(int maxAttempts, @NonNull IntFunction<Duration> durationGetter) {
        return with(i -> i<maxAttempts, durationGetter);
    }

    @NonNull
    static ReconnectionPolicy with(int maxAttempts, @NonNull Duration fixDuration) {
        return with(i -> i<maxAttempts, i -> fixDuration);
    }
}
