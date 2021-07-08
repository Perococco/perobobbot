package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.SubscriptionEntityBase;
import perobobbot.lang.SubscriptionData;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "SUBSCRIPTION")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionEntity extends SubscriptionEntityBase {


    public SubscriptionEntity(@NonNull SubscriptionData subscriptionData) {
        super(subscriptionData.getPlatform(),"", subscriptionData.getSubscriptionType(),subscriptionData.getConditions());
    }



}
