package perobobbot.data.domain.base;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.Platform;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionEntityBase extends PersistentObjectWithUUID {

    /**
     * The platform this subscription applies to
     */
    @Column(name = "PLATFORM",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull Platform platform;

    /**
     * The id of the subscription. This is the id returned by the platform
     */
    @Column(name = "SUBSCRIPTION_ID",nullable = false,unique = true)
    @NotBlank
    private @NonNull String subscriptionId = "";

    /**
     * The type of the subscription (see subscription doc for each platform)
     */
    @Column(name = "TYPE",nullable = false)
    @NotBlank
    private @NonNull String type = "";

    /**
     * The condition of the subscription.
     */
    @ElementCollection
    @MapKeyColumn(name="CRITERIA")
    @Column(name = "VALUE",nullable = false)
    @CollectionTable(name="CONDITION", joinColumns=@JoinColumn(name="ID"))
    private @NonNull Map<String,String> condition = new HashMap<>();

    public SubscriptionEntityBase(@NonNull Platform platform,
                                  @NonNull String subscriptionId,
                                  @NonNull String type,
                                  @NonNull Map<String,String> condition) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.subscriptionId = subscriptionId;
        this.type = type;
        this.condition = new HashMap<>(condition);
    }

    public @NonNull SubscriptionView toView() {
        return new SubscriptionView(getUuid(), platform, type, ImmutableMap.copyOf(condition),subscriptionId);
    }
}
