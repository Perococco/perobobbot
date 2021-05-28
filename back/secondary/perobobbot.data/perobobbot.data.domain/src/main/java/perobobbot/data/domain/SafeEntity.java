package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.SafeEntityBase;
import perobobbot.lang.PointType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "SAFE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SafeEntity extends SafeEntityBase {

    public SafeEntity(@NonNull ViewerIdentityEntity viewerIdentity,
                      @NonNull String channelName,
                      @NonNull PointType type) {
        super(viewerIdentity, channelName, type);
    }

    public @NonNull TransactionEntity createTransaction(long requestedAmount, @NonNull Instant expirationDate) {
        this.performWithdraw(requestedAmount);
        final var transaction = new TransactionEntity(this, requestedAmount, expirationDate);
        this.getTransactions().add(transaction);
        return transaction;
    }



}
