package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.domain.SubscriptionEntity;
import perobobbot.data.domain.UserSubscriptionEntity;
import perobobbot.data.domain.base.SubscriptionEntityBase;
import perobobbot.data.domain.base.UserSubscriptionEntityBase;
import perobobbot.data.jpa.repository.SubscriptionRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.jpa.repository.UserSubscriptionRepository;
import perobobbot.data.service.SubscriptionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Conditions;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

import java.util.Optional;
import java.util.UUID;


@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPASubscriptionService implements SubscriptionService {

    private final @NonNull SubscriptionRepository subscriptionRepository;
    private final @NonNull UserSubscriptionRepository userSubscriptionRepository;
    private final @NonNull UserRepository userRepository;

    @Override
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptions(@NonNull String login) {
        return userSubscriptionRepository.findByOwner_Login(login)
                                         .map(UserSubscriptionEntityBase::toView)
                                         .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform) {
        return userSubscriptionRepository.findByOwner_LoginAndSubscription_Platform(login, platform)
                                         .map(UserSubscriptionEntityBase::toView)
                                         .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform) {
        return subscriptionRepository.findByPlatform(platform)
                                     .map(SubscriptionEntityBase::toView)
                                     .collect(ImmutableList.toImmutableList());
    }

    @Override
    @Transactional
    public @NonNull UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login) {
        final var existing = userSubscriptionRepository.findByOwner_LoginAndSubscriptionUuid(login, subscriptionId);

        if (existing.isPresent()) {
            return existing.get().toView();
        }

        final var subscription = subscriptionRepository.getByUuid(subscriptionId);
        final var user = userRepository.getByLogin(login);

        return userSubscriptionRepository.save(new UserSubscriptionEntity(user, subscription)).toView();
    }

    @Override
    @Transactional
    public void updateSubscriptionWithPlatformAnswer(@NonNull UUID subscriptionDbId, @NonNull SubscriptionIdentity subscriptionIdentity) {
        final var subscription = subscriptionRepository.getByUuid(subscriptionDbId);
        subscription.setCallbackUrl(subscriptionIdentity.getCallbackUrl());
        subscription.setSubscriptionId(subscriptionIdentity.getSubscriptionId());
        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public @NonNull SubscriptionView getOrCreateSubscription(@NonNull SubscriptionData subscriptionData) {
        final var existing = findSubscriptionEntity(subscriptionData);

        if (existing.isPresent()) {
            return existing.get().toView();
        }

        final var entity = new SubscriptionEntity(subscriptionData);
        return subscriptionRepository.save(entity).toView();
    }

    @Override
    public @NonNull Optional<SubscriptionView> findSubscription(@NonNull SubscriptionData subscriptionData) {
        return findSubscriptionEntity(subscriptionData).map(SubscriptionEntityBase::toView);
    }

    private @NonNull Optional<SubscriptionEntity> findSubscriptionEntity(@NonNull SubscriptionData subscriptionData) {
        return subscriptionRepository.findByPlatformAndType(
                subscriptionData.getPlatform(),
                subscriptionData.getSubscriptionType())
                                     .filter(s -> Conditions.with(s.getCondition()).equals(subscriptionData.getConditions()))
                                     .findAny();
    }

    @Override
    @Transactional
    public @NonNull Optional<SubscriptionView> cleanSubscription(@NonNull UUID id) {
        final var subscription = subscriptionRepository.getByUuid(id).toView();
        final var shouldBeDeleted = userSubscriptionRepository.countAllBySubscription_Uuid(id) == 0;
        if (shouldBeDeleted) {
            subscriptionRepository.deleteByUuid(id);
            return Optional.of(subscription);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void removeUserFromSubscription(@NonNull UUID id, @NonNull String login) {
        userSubscriptionRepository.deleteAllByOwner_LoginAndSubscription_Uuid(login, id);
    }
}
