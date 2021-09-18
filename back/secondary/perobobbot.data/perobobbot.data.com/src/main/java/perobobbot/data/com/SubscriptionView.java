package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

import java.util.UUID;

@Value
public class SubscriptionView {
    /**
     * id in our own DB
     */
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;
    /**
     * Id of the subscription on the platform
     */
    @NonNull String subscriptionId;
    /**
     * The url used for this subscription
     */
    @NonNull String callbackUrl;

    public @NonNull SubscriptionData createData() {
        return new SubscriptionData(platform,subscriptionType,conditions);
    }

    public boolean hasPlatformId() {
        return !subscriptionId.isEmpty();
    }
}
