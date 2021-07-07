package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import reactor.core.publisher.Mono;

public interface TwitchEventSubLister {

    @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions();

}
