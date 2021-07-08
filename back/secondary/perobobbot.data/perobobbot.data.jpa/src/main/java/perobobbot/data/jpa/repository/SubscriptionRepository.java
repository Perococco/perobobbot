package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.lang.Platform;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @NonNull Optional<SubscriptionEntity> findByTypeAndCondition(@NonNull String subscriptionType, @NonNull Map<String,String> conditionId);

    @NonNull Stream<SubscriptionEntity> findByPlatform(@NonNull Platform platform);

    void deleteByUuid(@NonNull UUID subscriptionId);

    @NonNull SubscriptionEntity getByUuid(@NonNull UUID uuid);

}

