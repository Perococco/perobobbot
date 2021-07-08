package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

import java.util.UUID;

@Value
public class SubscriptionView {
    @NonNull UUID id;
    @NonNull Platform platform;
    @NonNull String subscriptionType;
    @NonNull Conditions conditions;
    @NonNull String subscriptionId;

    public @NonNull SubscriptionData createData() {
        return new SubscriptionData(platform,subscriptionType,conditions);
    }
}
