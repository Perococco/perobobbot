package perobobbot.common.lang;

import lombok.NonNull;

public class SubscriptionHolder {

    private Subscription subscription = null;

    public void replaceWith(@NonNull Subscription subscription) {
        unsubscribe();
        this.subscription = subscription;
    }

    public void replaceWith(@NonNull Subscription... subscriptions) {
        unsubscribe();
        this.subscription = Subscription.join(subscriptions);
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
