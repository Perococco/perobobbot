package perobobbot.data.security.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.SubscriptionService;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredSubscriptionService implements SubscriptionService {

    private final @NonNull @EventService SubscriptionService subscriptionService;

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #login")
    public @NonNull Stream<UserSubscriptionView> listAllSubscriptions(@NonNull String login) {
        return subscriptionService.listAllSubscriptions(login);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<SubscriptionView> findSubscription(@NonNull String subscriptionType, @NonNull String conditionId) {
        return subscriptionService.findSubscription(subscriptionType, conditionId);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login) {
        return subscriptionService.addUserToSubscription(subscriptionId, login);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public SubscriptionView createSubscription(@NonNull String subscriptionTwitchId, @NonNull String subscriptionType, @NonNull String conditionId) {
        return subscriptionService.createSubscription(subscriptionTwitchId, subscriptionType, conditionId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<SubscriptionView> cleanSubscription(@NonNull UUID id) {
        return subscriptionService.cleanSubscription(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserSubscription(@NonNull UUID id, @NonNull String login) {
        subscriptionService.deleteUserSubscription(id, login);
    }
}
