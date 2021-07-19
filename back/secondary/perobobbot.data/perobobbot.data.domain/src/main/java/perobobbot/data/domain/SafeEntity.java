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

    SafeEntity(@NonNull ViewerIdentityEntity viewerIdentity,
                      @NonNull String channelName) {
        super(viewerIdentity, channelName);
    }

    public @NonNull TransactionEntity createTransaction(@NonNull PointType pointType, long requestedAmount, @NonNull Instant expirationDate) {
        this.performWithdraw(pointType, requestedAmount);
        final var transaction = new TransactionEntity(this, pointType, requestedAmount, expirationDate);
        this.getTransactions().add(transaction);
        return transaction;
    }

}
