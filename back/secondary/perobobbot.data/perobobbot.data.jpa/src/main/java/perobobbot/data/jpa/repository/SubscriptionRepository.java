package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.SubscriptionEntity;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @NonNull Optional<SubscriptionEntity> findByTypeAndCondition(@NonNull String subscriptionType, @NonNull String conditionId);

    void deleteByUuid(@NonNull UUID subscriptionId);

    @NonNull SubscriptionEntity getByUuid(@NonNull UUID uuid);

}

