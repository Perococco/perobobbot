package perobobbot.lang;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Try0;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public interface Subscription {

    Subscription NONE = () -> { };

    void unsubscribe();


    default @NonNull Subscription and(@NonNull Subscription other) {
        return () -> {
            this.unsubscribe();
            other.unsubscribe();
        };
    }

    @NonNull
    static Subscription multi(@NonNull Subscription... subscriptions) {
        return multi(ImmutableList.copyOf(subscriptions));
    }

    @NonNull
    static Subscription multi(@NonNull ImmutableCollection<Subscription> subscriptions) {
        return switch (subscriptions.size()) {
            case 0, 1 -> subscriptions.stream().findAny().orElse(NONE);
            default -> () -> subscriptions.forEach(Subscription::unsubscribe);
        };
    }

    Collector<Subscription, ?, Subscription> COLLECTOR = Collectors.collectingAndThen(
            ImmutableList.toImmutableList(),
            Subscription::multi
    );

    default void run(@NonNull Runnable runnable) {
        try {
            runnable.run();
        } finally {
            this.unsubscribe();
        }
    }


    static void subscribeAndRun(@NonNull Function0<Subscription> subscriber, @NonNull Runnable runnable) {
        var subscription = subscriber.f();
        try {
            runnable.run();
        } finally {
            subscription.unsubscribe();
        }
    }

    static <T> @NonNull T subscribeAndCall(@NonNull Function0<Subscription> subscriber, @NonNull Function0<T> call) {
        var subscription = subscriber.f();
        try {
            return call.f();
        } finally {
            subscription.unsubscribe();
        }
    }

    static <T,E extends Throwable> @NonNull T subscribeAndTry(@NonNull Function0<Subscription> subscriber, @NonNull Try0<T,E> call) throws E {
        var subscription = subscriber.f();
        try {
            return call.f();
        } finally {
            subscription.unsubscribe();
        }
    }
}
