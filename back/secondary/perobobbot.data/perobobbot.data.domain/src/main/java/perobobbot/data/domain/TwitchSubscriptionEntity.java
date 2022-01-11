package perobobbot.data.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table(name = "TWITCH_SUBSCRIPTION")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TwitchSubscriptionEntity extends PersistentObjectWithUUID {


    /**
     * The platform this subscription applies to
     */
    @Column(name = "PLATFORM",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull Platform platform;

    /**
     * The type of the subscription (see subscription doc for each platform)
     */
    @Column(name = "TYPE",nullable = false)
    @NotBlank
    private @NonNull String type = "";

    /**
     * The id of the subscription. This is the id returned by the platform
     */
    @Column(name = "SUBSCRIPTION_ID",nullable = false)
    @Getter @Setter
    private @NonNull String subscriptionId = "";

    /**
     * The type of the subscription (see subscription doc for each platform)
     */
    @Column(name = "CALLBACK_URL",nullable = false)
    @Getter @Setter
    private @NonNull String callbackUrl = "";

    /**
     * The condition of the subscription.
     */
    @ElementCollection
    @MapKeyColumn(name="CRITERIA")
    @Column(name = "VALUE",nullable = false)
    @CollectionTable(name="CONDITION", joinColumns=@JoinColumn(name="ID"))
    @Getter
    private @NonNull Map<String,String> condition = new HashMap<>();

    public TwitchSubscriptionEntity(@NonNull SubscriptionData subscriptionData) {
        this(subscriptionData.getPlatform(),"", subscriptionData.getSubscriptionType(),subscriptionData.getConditions(),"");
    }

    private TwitchSubscriptionEntity(@NonNull Platform platform,
                                        @NonNull String subscriptionId,
                                        @NonNull String type,
                                        @NonNull Conditions conditions,
                                        @NonNull String callbackUrl) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.subscriptionId = subscriptionId;
        this.type = type;
        this.callbackUrl = callbackUrl;
        this.condition = conditions.copyAsMap();
    }

    public @NonNull SubscriptionView toView() {
        return new SubscriptionView(getUuid(), platform, type, Conditions.with(condition),subscriptionId,callbackUrl);
    }




}
