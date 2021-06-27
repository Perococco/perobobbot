package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.data.domain.UserSubscriptionEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, Long> {

    @NonNull Optional<UserSubscriptionEntity> findByOwner_LoginAndSubscriptionUuid(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Stream<UserSubscriptionEntity> findByOwner_Login(@NonNull String login);

    void deleteAllByOwner_LoginAndSubscription_Uuid(@NonNull String login, @NonNull UUID subscriptionId);

    long countAllBySubscription_Uuid(@NonNull UUID subscriptionId);
}

