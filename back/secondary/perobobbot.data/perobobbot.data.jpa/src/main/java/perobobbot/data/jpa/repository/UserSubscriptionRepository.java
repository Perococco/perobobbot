package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perobobbot.data.domain.UserSubscriptionEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, Long> {

    @NonNull Optional<UserSubscriptionEntity> findByOwner_LoginAndSubscriptionUuid(@NonNull String login, @NonNull UUID subscriptionId);

    @NonNull Stream<UserSubscriptionEntity> findByOwner_Login(@NonNull String login);

    @NonNull Stream<UserSubscriptionEntity> findByOwner_LoginAndSubscription_Platform(@NonNull String login, @NonNull Platform platform);

    void deleteAllByOwner_LoginAndSubscription_Uuid(@NonNull String login, @NonNull UUID subscriptionId);

    long countAllBySubscription_Uuid(@NonNull UUID subscriptionId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update SubscriptionEntity s set s.callbackUrl = '' where s.callbackUrl NOT LIKE CONCAT(:callbackHost,'%') ")
    void clearCallbackUrlIfDoesNotStartWith(@Param("callbackHost") @NonNull String callbackHost);
}

