package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.TwitchSubscriptionEntityBase;
import perobobbot.lang.SubscriptionData;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "TWITCH_SUBSCRIPTION")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TwitchSubscriptionEntity extends TwitchSubscriptionEntityBase {


    public TwitchSubscriptionEntity(@NonNull SubscriptionData subscriptionData) {
        super(subscriptionData.getPlatform(),"", subscriptionData.getSubscriptionType(),subscriptionData.getConditions(),"");
    }



}
