package perobobbot.twitch.eventsub.manager;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Nil;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;
import perobobbot.twitch.eventsub.api.EventSubHandler;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import perobobbot.twitch.eventsub.api.subscription.Subscription;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TwitchEventSubManager implements PlatformEventSubManager {

    private final @NonNull EventSubHandler eventSubHandler;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull Set<String> getSubscriptionTypes() {
        return Arrays.stream(SubscriptionType.values()).map(SubscriptionType::getIdentification).collect(
                Collectors.toSet());
    }

    @Override
    public @NonNull Mono<Nil> deleteSubscription(@NonNull String login, @NonNull UUID subscriptionId) {
        return eventSubHandler.handleSubscriptionDeletion(login, subscriptionId);
    }

    @Override
    public @NonNull Mono<UserSubscriptionView> createSubscription(@NonNull String login, @NonNull String subscriptionType, @NonNull ImmutableMap<String, String> condition) {
        final var type = IdentifiedEnumTools.getEnum(subscriptionType, SubscriptionType.class);
        final Subscription subscription = type.create(condition);
        return eventSubHandler.handleCreateSubscription(login,subscription);
    }
}
