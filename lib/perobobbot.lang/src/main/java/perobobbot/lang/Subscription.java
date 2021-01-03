package perobobbot.lang;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public interface Subscription {

    Subscription NONE = () -> { };

    void unsubscribe();

    @NonNull
    static Subscription join(@NonNull Subscription... subscriptions) {
        return join(ImmutableList.copyOf(subscriptions));
    }

    @NonNull
    static Subscription join(@NonNull ImmutableCollection<Subscription> subscriptions) {
        return switch (subscriptions.size()) {
            case 0, 1 -> subscriptions.stream().findAny().orElse(NONE);
            default -> () -> subscriptions.forEach(Subscription::unsubscribe);
        };
    }

    Collector<Subscription, ?, Subscription> COLLECTOR = Collectors.collectingAndThen(
            ImmutableList.toImmutableList(),
            Subscription::join
    );

}
