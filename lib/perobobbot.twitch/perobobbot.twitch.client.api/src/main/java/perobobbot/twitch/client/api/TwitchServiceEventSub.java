package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredToken;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Mono;

public interface TwitchServiceEventSub {

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<TwitchSubscriptionData> createEventSubSubscription(@NonNull TwitchSubscriptionRequest request);

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull String id);

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions();

}
