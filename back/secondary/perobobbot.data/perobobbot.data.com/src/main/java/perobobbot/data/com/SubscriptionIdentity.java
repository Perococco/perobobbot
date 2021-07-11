package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

public interface SubscriptionIdentity {

    @NonNull String getSubscriptionId();

    @NonNull Platform getPlatform();
    @NonNull String getSubscriptionType();
    @NonNull Conditions getConditions();
    @NonNull String getCallbackUrl();

    boolean isValid();

    default @NonNull SubscriptionData createData() {
        return new SubscriptionData(getPlatform(),getSubscriptionType(),getConditions());
    }
}
