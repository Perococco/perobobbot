package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.PointEntity;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class WithdrawEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "TARGET_ID",nullable = false)
    @NonNull
    private PointEntity target;

    private long amount;

    @NonNull
    private Instant expirationTime;

    protected WithdrawEntityBase(@NonNull PointEntity target, long amount, @NonNull Instant expirationTime) {
        super(UUID.randomUUID());
        this.target = target;
        this.amount = amount;
        this.expirationTime = expirationTime;
    }
}
