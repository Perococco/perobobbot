package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.UserSubscriptionEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "USER_SUBSCRIPTION")
@Entity
@NoArgsConstructor
public class UserSubscriptionEntity extends UserSubscriptionEntityBase {

    protected UserSubscriptionEntity(@NonNull UserEntity owner, @NonNull SubscriptionEntity subscription) {
        super(owner, subscription);
    }
}
