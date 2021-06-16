package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionEntityBase extends PersistentObjectWithUUID {

    @Column(name = "SUBSCRIPTION_ID",nullable = false,unique = true)
    @NotBlank
    private @NonNull String subscriptionId = "";

    @Column(name = "TYPE",nullable = false)
    @NotBlank
    private @NonNull String type = "";

    @Column(name = "CONDITION",nullable = false)
    @NotBlank
    private @NonNull String condition = "";

    public SubscriptionEntityBase(@NonNull String subscriptionId, @NonNull String type, @NonNull String condition) {
        super(UUID.randomUUID());
        this.subscriptionId = subscriptionId;
        this.type = type;
        this.condition = condition;
    }
}
