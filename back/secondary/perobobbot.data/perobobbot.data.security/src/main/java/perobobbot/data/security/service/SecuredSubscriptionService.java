package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.lang.Platform;

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
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #login")
    public @NonNull Stream<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform) {
        return subscriptionService.listAllSubscriptionsByPlatform(login,platform);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<SubscriptionView> findSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull String conditionId) {
        return subscriptionService.findSubscription(platform, subscriptionType, conditionId);
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
    public SubscriptionView createSubscription(@NonNull Platform platform, @NonNull String subscriptionTwitchId, @NonNull String subscriptionType, @NonNull String conditionId) {
        return subscriptionService.createSubscription(platform, subscriptionTwitchId, subscriptionType, conditionId);
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

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform) {
        return subscriptionService.listAllByPlatform(platform);
    }
}
