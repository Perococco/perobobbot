package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownSafe;
import perobobbot.data.com.UnknownTransaction;
import perobobbot.data.domain.SafeEntity;
import perobobbot.data.domain.TransactionEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


    @NonNull Optional<TransactionEntity> findByUuid(@NonNull UUID id);

    default @NonNull TransactionEntity getByUuid(@NonNull UUID id) {
        return findByUuid(id).orElseThrow(() -> new UnknownTransaction(id));
    }

}
