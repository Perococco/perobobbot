package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "USER_TWITCH_SUBSCRIPTION")
@Entity
@NoArgsConstructor
public class UserTwitchSubscriptionEntity extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID",nullable = false)
    private @NonNull UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_ID",nullable = false)
    private @NonNull TwitchSubscriptionEntity subscription;

    protected UserTwitchSubscriptionEntity(@NonNull UserEntity owner, @NonNull TwitchSubscriptionEntity subscription) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.subscription = subscription;
    }

    public @NonNull UserSubscriptionView toView() {
        return new UserSubscriptionView(getUuid(), owner.getLogin(), subscription.toView());
    }
}
