package perobobbot.common.lang;

import lombok.NonNull;

public class SubscriptionHolder {

    private Subscription subscription = null;

    public void replace(@NonNull Subscription subscription) {
        unsubscribe();
        this.subscription = subscription;
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
