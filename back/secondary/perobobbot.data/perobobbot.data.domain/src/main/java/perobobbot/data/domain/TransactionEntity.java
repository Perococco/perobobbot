package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.ExpiredTransaction;
import perobobbot.data.com.InvalidTransactionState;
import perobobbot.data.domain.base.TransactionEntityBase;
import perobobbot.lang.PointType;
import perobobbot.lang.TransactionState;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionEntity extends TransactionEntityBase {

    public TransactionEntity(@NonNull SafeEntity target, @NonNull PointType pointType, long amount, @NonNull Instant expirationTime) {
        super(target, pointType, amount, expirationTime);
    }

    public @NonNull TransactionEntity rollback() {
        this.checkIsInState(TransactionState.PENDING);
        this.setState(TransactionState.CANCELLED);
        this.getTarget().addToAmount(this.getPointType(), this.getAmount());
        return this;
    }

    public @NonNull TransactionEntity  complete(@NonNull Instant executionTime) {
        this.checkIsInState(TransactionState.PENDING);
        this.checkNotExpired(executionTime);
        this.setState(TransactionState.COMPLETED);
        return this;
    }

    public @NonNull TransactionEntity removeFromSafe() {
        getTarget().getTransactions().remove(this);
        this.setState(TransactionState.DETACHED);
        return this;
    }

    public boolean isExpired(@NonNull Instant now) {
        return now.isAfter(this.getExpirationTime());
    }

    public void checkNotExpired(@NonNull Instant executionTime) {
        if (isExpired(executionTime)) {
            throw new ExpiredTransaction(getUuid());
        }
    }

    public void checkIsInState(@NonNull TransactionState state) {
        if (this.getState() != state) {
            throw new InvalidTransactionState(this.getState());
        }
    }
    public void checkIsNotInState(@NonNull TransactionState state) {
        if (this.getState() == state) {
            throw new InvalidTransactionState(this.getState());
        }
    }
}
