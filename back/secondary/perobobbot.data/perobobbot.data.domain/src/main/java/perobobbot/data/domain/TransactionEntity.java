package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import perobobbot.data.com.ExpiredTransaction;
import perobobbot.data.com.InvalidTransactionState;
import perobobbot.lang.PointType;
import perobobbot.lang.TransactionInfo;
import perobobbot.lang.TransactionState;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionEntity extends PersistentObjectWithUUID {

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

    protected TransactionEntity(@NonNull SafeEntity target,
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
        return new TransactionInfo(getUuid(), target.getUuid(), pointType, amount, state, expirationTime);
    }

    public @NonNull TransactionEntity rollback() {
        this.checkIsInState(TransactionState.PENDING);
        this.state = TransactionState.CANCELLED;
        this.target.addToAmount(pointType,amount);
        return this;
    }

    public @NonNull TransactionEntity  complete(@NonNull Instant executionTime) {
        this.checkIsInState(TransactionState.PENDING);
        this.checkNotExpired(executionTime);
        this.state = TransactionState.COMPLETED;
        return this;
    }

    public @NonNull TransactionEntity removeFromSafe() {
        target.removeTransaction(this.getUuid());
        this.state = TransactionState.DETACHED;
        return this;
    }

    public boolean isExpired(@NonNull Instant now) {
        return now.isAfter(this.expirationTime);
    }

    public void checkNotExpired(@NonNull Instant executionTime) {
        if (isExpired(executionTime)) {
            throw new ExpiredTransaction(getUuid());
        }
    }

    public void checkIsInState(@NonNull TransactionState state) {
        if (this.state != state) {
            throw new InvalidTransactionState(this.state);
        }
    }
    public void checkIsNotInState(@NonNull TransactionState state) {
        if (this.state == state) {
            throw new InvalidTransactionState(this.state);
        }
    }
}
