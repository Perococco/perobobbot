package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionData;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
public class SecuredSubscriptionService implements SubscriptionService {

    private final @NonNull @EventService SubscriptionService subscriptionService;

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #login")
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptions(@NonNull String login) {
        return subscriptionService.listAllSubscriptions(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #login")
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform) {
        return subscriptionService.listAllSubscriptionsByPlatform(login,platform);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull Optional<SubscriptionView> findSubscription(@NonNull SubscriptionData subscriptionData) {
        return subscriptionService.findSubscription(subscriptionData);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public UserSubscriptionView addUserToSubscription(@NonNull UUID subscriptionId, @NonNull String login) {
        return subscriptionService.addUserToSubscription(subscriptionId, login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull SubscriptionView getOrCreateSubscription(@NonNull SubscriptionData subscriptionData) {
        return subscriptionService.getOrCreateSubscription(subscriptionData);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull Optional<SubscriptionView> cleanSubscription(@NonNull UUID id) {
        return subscriptionService.cleanSubscription(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void removeUserFromSubscription(@NonNull UUID id, @NonNull String login) {
        subscriptionService.removeUserFromSubscription(id, login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateSubscriptionWithPlatformAnswer(@NonNull UUID subscriptionDbId, @NonNull SubscriptionIdentity subscriptionIdentity) {
        subscriptionService.updateSubscriptionWithPlatformAnswer(subscriptionDbId,subscriptionIdentity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform) {
        return subscriptionService.listAllByPlatform(platform);
    }
}
