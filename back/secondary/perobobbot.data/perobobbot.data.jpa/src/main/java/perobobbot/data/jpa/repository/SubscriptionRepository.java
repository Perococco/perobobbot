package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.lang.Platform;

import java.util.UUID;
import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @NonNull Stream<SubscriptionEntity> findByPlatformAndType(
            @NonNull Platform platform,
            @NonNull String subscriptionType);

    @NonNull Stream<SubscriptionEntity> findByPlatform(@NonNull Platform platform);

    void deleteByUuid(@NonNull UUID subscriptionId);

    @NonNull SubscriptionEntity getByUuid(@NonNull UUID uuid);

}

