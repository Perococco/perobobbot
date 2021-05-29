package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownTransaction;
import perobobbot.data.domain.TransactionEntity;
import perobobbot.lang.TransactionState;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


    @NonNull Optional<TransactionEntity> findByUuid(@NonNull UUID id);

    @NonNull List<TransactionEntity> findAllByStateEqualsAndExpirationTimeBefore(@NonNull TransactionState state, @NonNull Instant now);

    default @NonNull TransactionEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownTransaction(id));
    }

}
