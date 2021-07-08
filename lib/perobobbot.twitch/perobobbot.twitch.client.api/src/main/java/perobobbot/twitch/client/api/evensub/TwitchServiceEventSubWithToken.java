package perobobbot.twitch.client.api.evensub;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.oauth.ClientApiToken;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Mono;

public interface TwitchServiceEventSubWithToken {

    @NonNull Mono<TwitchSubscriptionData> createEventSubSubscription(@NonNull ClientApiToken token, @NonNull TwitchSubscriptionRequest request);

    @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull ClientApiToken token, @NonNull String id);

    @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions(@NonNull ClientApiToken token);

}
