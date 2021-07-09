package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.SafeEntity;
import perobobbot.lang.PointType;
import perobobbot.lang.TransactionInfo;
import perobobbot.lang.TransactionState;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class TransactionEntityBase extends PersistentObjectWithUUID {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TARGET_ID",nullable = false)
    @NonNull
    private SafeEntity target;

    @Column(name = "TYPE",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private PointType pointType;

    @Column(name = "AMOUNT")
    private long amount;

    @NonNull
    @Column(name = "EXPIRATION_DATE",nullable = false)
    private Instant expirationTime;

    @Column(name = "STATE",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private TransactionState state = TransactionState.PENDING;

    protected TransactionEntityBase(@NonNull SafeEntity target,
                                    @NonNull PointType pointType,
                                    long amount,
                                    @NonNull Instant expirationTime) {
        super(UUID.randomUUID());
        this.target = target;
        this.pointType = pointType;
        this.amount = amount;
        this.expirationTime = expirationTime;
    }

    public @NonNull TransactionInfo toView() {
        return new TransactionInfo(getUuid(), target.getUuid(), pointType, amount, getState(), expirationTime);
    }
}
