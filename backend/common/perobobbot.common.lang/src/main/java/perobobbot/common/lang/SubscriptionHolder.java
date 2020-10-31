package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function0;

public class SubscriptionHolder {

    private Subscription subscription = null;

    public void replaceWith(@NonNull Function0<? extends Subscription> subscription) {
        unsubscribe();
        this.subscription = subscription.f();
    }

    public boolean hasSubscription() {
        return subscription != null;
    }

    public void unsubscribe() {
        if (this.subscription != null) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
    }

}
