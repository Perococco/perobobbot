package perobobbot.data.domain.base;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import perobobbot.data.com.NotEnoughPoints;
import perobobbot.data.domain.TransactionEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Balance;
import perobobbot.lang.PointType;
import perobobbot.lang.Safe;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.*;

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
    @MapKeyType(@Type(type = "perobobbot.persistence.type.IdentifiedEnumType"))
    @MapKeyColumn(name="POINT_TYPE")
    @Column(name = "CREDIT",nullable = false)
    @CollectionTable(name="SAFE_CREDIT", joinColumns=@JoinColumn(name="ID"))
    private @NonNull Map<PointType,Long> credits = new HashMap<>();

    @OneToMany(mappedBy = "target",cascade = CascadeType.ALL,orphanRemoval = true)
    private @NonNull List<TransactionEntity> transactions = new ArrayList<>();

    public SafeEntityBase(@NonNull ViewerIdentityEntity viewerIdentity, @NonNull String channelName) {
        super(UUID.randomUUID());
        this.viewerIdentity = viewerIdentity;
        this.channelName = channelName;
        this.credits = new HashMap<>();
    }

    public void checkEnoughBalance(@NonNull PointType pointType, long requestedAmount) {
        final var balance = this.credits.getOrDefault(pointType,0l);
        if (balance < requestedAmount) {
            throw new NotEnoughPoints(viewerIdentity.toView(), getChannelName(), pointType, requestedAmount);
        }
    }

    public void performWithdraw(@NonNull PointType pointType, long requestedAmount) {
        if (requestedAmount<=0) {
            return;
        }
        this.checkEnoughBalance(pointType,requestedAmount);
        final var balance = this.credits.getOrDefault(pointType,0l);
        this.credits.put(pointType,balance-requestedAmount);
    }

    public void addToAmount(@NonNull PointType pointType, long amountToAdd) {
        if (amountToAdd<=0) {
            return;
        }
        final var balance = this.credits.getOrDefault(pointType,0l);
        this.credits.put(pointType,balance+amountToAdd);
    }


    public @NonNull Safe toView() {
        return Safe.builder()
                   .id(getUuid())
                   .viewerIdentity(viewerIdentity.toView())
                   .channelName(channelName)
                   .credits(ImmutableMap.copyOf(this.credits))
                   .build();
    }

    public @NonNull Balance toBalance(@NonNull PointType pointType) {
        return new Balance(getUuid(),pointType,this.credits.getOrDefault(pointType,0l));
    }

}
