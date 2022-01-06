package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.UserTwitchSubscriptionEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "USER_TWITCH_SUBSCRIPTION")
@Entity
@NoArgsConstructor
public class UserTwitchSubscriptionEntity extends UserTwitchSubscriptionEntityBase {

    public UserTwitchSubscriptionEntity(@NonNull UserEntity owner, @NonNull TwitchSubscriptionEntity subscription) {
        super(owner, subscription);
    }
}
