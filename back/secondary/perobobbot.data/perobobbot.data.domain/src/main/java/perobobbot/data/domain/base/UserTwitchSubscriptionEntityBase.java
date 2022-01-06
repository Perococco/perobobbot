package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.domain.TwitchSubscriptionEntity;
import perobobbot.data.domain.UserEntity;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class UserTwitchSubscriptionEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID",nullable = false)
    private @NonNull UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_ID",nullable = false)
    private @NonNull TwitchSubscriptionEntity subscription;

    protected UserTwitchSubscriptionEntityBase(@NonNull UserEntity owner, @NonNull TwitchSubscriptionEntity subscription) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.subscription = subscription;
    }

    public @NonNull UserSubscriptionView toView() {
        return new UserSubscriptionView(getUuid(), owner.getLogin(), subscription.toView());
    }
}
