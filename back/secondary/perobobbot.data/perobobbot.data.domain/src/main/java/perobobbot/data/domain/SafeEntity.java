package perobobbot.data.domain;

import com.google.common.collect.ImmutableMap;
import lombok.*;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import perobobbot.data.com.NotEnoughPoints;
import perobobbot.data.com.PromotionManager;
import perobobbot.lang.Balance;
import perobobbot.lang.PointType;
import perobobbot.lang.Safe;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "SAFE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED)
public class SafeEntity extends PersistentObjectWithUUID {


    @ManyToOne
    @JoinColumn(name = "PLATFORM_USER_ID",nullable = false)
    private @NonNull PlatformUserEntity<?> platformUser;

    @Column(name = "CHANNEL_ID", nullable = false)
    private @NonNull String channelId;

    @ElementCollection
    @MapKeyColumn(name = "POINT_TYPE")
    @MapKeyType(value = @Type(type = "perobobbot.persistence.type.IdentifiedEnumType"))
    @Column(name = "CREDIT", nullable = false)
    @CollectionTable(name = "SAFE_CREDIT", joinColumns = @JoinColumn(name = "ID"))
    private @NonNull Map<PointType, Long> credits = new HashMap<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private @NonNull List<TransactionEntity> transactions = new ArrayList<>();


    public SafeEntity(@NonNull PlatformUserEntity<?> platformUser, @NonNull String channelId) {
        super(UUID.randomUUID());
        this.platformUser = platformUser;
        this.channelId = channelId;
        this.credits = Arrays.stream(PointType.values()).collect(Collectors.toMap(p -> p, PromotionManager::initialBalance));
    }

    public long getBalance(@NonNull PointType pointType) {
        final var promotion = PromotionManager.INSTANCE.getPromotion();
        return this.credits.computeIfAbsent(pointType, promotion::initialBalance);
    }

    public void checkEnoughBalance(@NonNull PointType pointType, long requestedAmount) {
        final var balance = this.getBalance(pointType);
        if (balance < requestedAmount) {
            throw new NotEnoughPoints(platformUser.toView(), channelId, pointType, requestedAmount);
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
        this.credits.put(pointType, Math.addExact(balance , amountToAdd));
    }


    public @NonNull Safe toView() {
        final var credits = Arrays.stream(PointType.values())
                                  .collect(ImmutableMap.toImmutableMap(p -> p, this::getBalance));
        return Safe.builder()
                   .id(getUuid())
                   .platformUserId(platformUser.getUuid())
                   .channelId(channelId)
                   .credits(credits)
                   .build();
    }

    public @NonNull Balance toBalance(@NonNull PointType pointType) {
        return new Balance(getUuid(), pointType, getBalance(pointType));
    }

    public @NonNull TransactionEntity createTransaction(@NonNull PointType pointType, long requestedAmount, @NonNull Instant expirationDate) {
        this.performWithdraw(pointType, requestedAmount);
        final var transaction = new TransactionEntity(this, pointType, requestedAmount, expirationDate);
        this.transactions.add(transaction);
        return transaction;
    }

    public void removeTransaction(@NonNull UUID transactionId) {
        this.transactions.removeIf(t -> t.getUuid().equals(transactionId));
    }
}
