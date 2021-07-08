package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptions(@NonNull String login) {
        return subscriptionService.listAllSubscriptions(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #login")
    public @NonNull ImmutableList<UserSubscriptionView> listAllSubscriptionsByPlatform(@NonNull String login, @NonNull Platform platform) {
        return subscriptionService.listAllSubscriptionsByPlatform(login,platform);
    }

    @Override
    @NonNull
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<SubscriptionView> findSubscription(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull ImmutableMap<String,String> conditionMap) {
        return subscriptionService.findSubscription(platform, subscriptionType, conditionMap);
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
    public SubscriptionView createSubscription(@NonNull Platform platform,
                                               @NonNull String subscriptionTwitchId,
                                               @NonNull String subscriptionType,
                                               @NonNull ImmutableMap<String,String> conditions) {
        return subscriptionService.createSubscription(platform, subscriptionTwitchId, subscriptionType, conditions);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<SubscriptionView> cleanSubscription(@NonNull UUID id) {
        return subscriptionService.cleanSubscription(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void removeUserFromSubscription(@NonNull UUID id, @NonNull String login) {
        subscriptionService.removeUserFromSubscription(id, login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateSubscriptionId(@NonNull UUID subscriptionDbId, @NonNull String subscriptionId) {
        subscriptionService.updateSubscriptionId(subscriptionDbId,subscriptionId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<SubscriptionView> listAllByPlatform(@NonNull Platform platform) {
        return subscriptionService.listAllByPlatform(platform);
    }
}
