package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.NotEnoughPoints;
import perobobbot.data.domain.TransactionEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Balance;
import perobobbot.lang.PointType;
import perobobbot.lang.Safe;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Column(name = "POINT_TYPE", nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull PointType type;

    @Column(name = "CREDIT")
    private long credit;

    @OneToMany(mappedBy = "target",cascade = CascadeType.ALL,orphanRemoval = true)
    private @NonNull List<TransactionEntity> transactions = new ArrayList<>();

    public SafeEntityBase(@NonNull ViewerIdentityEntity viewerIdentity, @NonNull String channelName, @NonNull PointType type) {
        super(UUID.randomUUID());
        this.viewerIdentity = viewerIdentity;
        this.channelName = channelName;
        this.type = type;
        this.credit = 0;
    }

    public void checkEnoughBalance(long requestedAmount) {
        if (this.credit < requestedAmount) {
            throw new NotEnoughPoints(viewerIdentity.toView(), getChannelName(), getType(), requestedAmount);
        }
    }

    public void performWithdraw(long requestedAmount) {
        this.checkEnoughBalance(requestedAmount);
        this.credit -= requestedAmount;
    }

    public @NonNull Safe toView() {
        return Safe.builder()
                   .id(getUuid())
                   .viewerIdentity(viewerIdentity.toView())
                   .channelName(channelName)
                   .credit(credit)
                   .type(type)
                   .build();
    }

    public @NonNull Balance toBalance() {
        return new Balance(this.toView(),this.credit);
    }

    public void addToAmount(long amountToAdd) {
        this.credit += amountToAdd;
    }
}
