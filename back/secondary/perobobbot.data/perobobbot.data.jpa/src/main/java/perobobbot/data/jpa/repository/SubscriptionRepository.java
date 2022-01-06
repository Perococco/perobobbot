package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.TwitchSubscriptionEntity;
import perobobbot.lang.Platform;

import java.util.UUID;
import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<TwitchSubscriptionEntity, Long> {

    @NonNull Stream<TwitchSubscriptionEntity> findByPlatformAndType(
            @NonNull Platform platform,
            @NonNull String subscriptionType);

    @NonNull Stream<TwitchSubscriptionEntity> findByPlatform(@NonNull Platform platform);

    void deleteByUuid(@NonNull UUID subscriptionId);

    @NonNull TwitchSubscriptionEntity getByUuid(@NonNull UUID uuid);

}

