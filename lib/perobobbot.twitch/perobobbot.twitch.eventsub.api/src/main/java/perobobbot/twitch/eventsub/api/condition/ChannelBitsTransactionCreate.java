package perobobbot.twitch.eventsub.api.condition;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChannelBitsTransactionCreate extends SingleConditionSubscription{

    @NonNull String extensionClientId;

    public ChannelBitsTransactionCreate(@NonNull String extensionClientId) {
        super(SubscriptionType.EXTENSION_BITS_TRANSACTION_CREATE,CriteriaType.EXTENSION_CLIENT_ID);
        this.extensionClientId = extensionClientId;
    }

    @Override
    protected String getConditionValue() {
        return extensionClientId;
    }
}
