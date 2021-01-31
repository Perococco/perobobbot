package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.WithdrawEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "WITHDRAW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawEntity extends WithdrawEntityBase {

    public WithdrawEntity(@NonNull PointEntity target, long amount, @NonNull Instant expirationTime) {
        super(target, amount, expirationTime);
    }


}
