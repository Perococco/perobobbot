package perobobbot.data.domain.base;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import perobobbot.data.com.NotEnoughPoints;
import perobobbot.lang.PointType;
import perobobbot.data.com.PromotionManager;
import perobobbot.data.domain.TransactionEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Balance;
import perobobbot.lang.Safe;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A safe contains the point of a user on a specific Platform channel
 */
@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class SafeEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "VIEWER_IDENTITY_ID")
    private @NonNull ViewerIdentityEntity viewerIdentity;

    @Column(name = "CHANNEL_NAME", nullable = false)
    private @NonNull String channelName;

    @ElementCollection
    @MapKeyColumn(name = "POINT_TYPE")
    @MapKeyType(value = @Type(type = "perobobbot.persistence.type.IdentifiedEnumType"))
    @Column(name = "CREDIT", nullable = false)
    @CollectionTable(name = "SAFE_CREDIT", joinColumns = @JoinColumn(name = "ID"))
    private @NonNull Map<PointType, Long> credits = new HashMap<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private @NonNull List<TransactionEntity> transactions = new ArrayList<>();


    public SafeEntityBase(@NonNull ViewerIdentityEntity viewerIdentity, @NonNull String channelName) {
        super(UUID.randomUUID());
        this.viewerIdentity = viewerIdentity;
        this.channelName = channelName;
        this.credits = Arrays.stream(PointType.values()).collect(Collectors.toMap(p -> p, PromotionManager::initialBalance));
    }

    public long getBalance(@NonNull PointType pointType) {
        final var promotion = PromotionManager.INSTANCE.getPromotion();
        return this.credits.computeIfAbsent(pointType, promotion::initialBalance);
    }

    public void checkEnoughBalance(@NonNull PointType pointType, long requestedAmount) {
        final var balance = this.getBalance(pointType);
        if (balance < requestedAmount) {
            throw new NotEnoughPoints(viewerIdentity.toView(), getChannelName(), pointType, requestedAmount);
        }
    }

    public void performWithdraw(@NonNull PointType pointType, long requestedAmount) {
        if (requestedAmount <= 0) {
            return;
        }
        this.checkEnoughBalance(pointType, requestedAmount);
        final var balance = getBalance(pointType);
        this.credits.put(pointType, balance - requestedAmount);
    }

    public void addToAmount(@NonNull PointType pointType, long amountToAdd) {
        if (amountToAdd <= 0) {
            return;
        }
        final var balance = getBalance(pointType);
        this.credits.put(pointType, balance + amountToAdd);
    }


    public @NonNull Safe toView() {
        final var credits = Arrays.stream(PointType.values())
                                  .collect(ImmutableMap.toImmutableMap(p -> p, this::getBalance));
        return Safe.builder()
                   .id(getUuid())
                   .viewerIdentity(viewerIdentity.toView())
                   .channelName(channelName)
                   .credits(credits)
                   .build();
    }

    public @NonNull Balance toBalance(@NonNull PointType pointType) {
        return new Balance(getUuid(), pointType, getBalance(pointType));
    }

}
