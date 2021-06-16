package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.data.domain.UserEntity;
import perobobbot.persistence.PersistentObjectWithUUID;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class UserSubscriptionEntityBase extends SimplePersistentObject {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID",nullable = false)
    private @NonNull UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_ID",nullable = false)
    private @NonNull SubscriptionEntity subscription;

    protected UserSubscriptionEntityBase(@NonNull UserEntity owner, @NonNull SubscriptionEntity subscription) {
        this.owner = owner;
        this.subscription = subscription;
    }
}
