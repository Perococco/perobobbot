package bot.common.lang;

import lombok.NonNull;

import java.util.stream.Stream;

/**
 * @author perococco
 **/
public interface Subscription {

    Subscription NONE = () -> {};

    void unsubscribe();

    @NonNull
    static Subscription join(@NonNull Subscription...subscriptions) {
        switch (subscriptions.length) {
            case 0 : return NONE;
            case 1 :
                return subscriptions[0];
            default:
                return () -> Stream.of(subscriptions).forEach(Subscription::unsubscribe);
        }
    }
}
