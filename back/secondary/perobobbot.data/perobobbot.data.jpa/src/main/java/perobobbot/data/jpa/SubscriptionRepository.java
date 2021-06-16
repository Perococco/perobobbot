package perobobbot.data.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.lang.Subscription;

import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @NonNull Stream<Subscription> findByType(@NonNull String subscriptionType);
}
