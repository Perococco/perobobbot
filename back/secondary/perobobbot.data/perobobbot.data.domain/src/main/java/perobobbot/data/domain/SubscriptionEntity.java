package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.SubscriptionEntityBase;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "SUBSCRIPTION")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionEntity extends SubscriptionEntityBase {

    public SubscriptionEntity(@NonNull Platform platform, @NonNull String subscriptionId, @NonNull String type, @NonNull String condition) {
        super(platform, subscriptionId, type, condition);
    }



}
