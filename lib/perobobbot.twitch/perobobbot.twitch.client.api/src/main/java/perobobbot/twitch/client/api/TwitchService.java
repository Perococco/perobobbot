package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredScope;
import perobobbot.oauth.RequiredToken;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TwitchService {

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Flux<Game> getGames(@NonNull GameSearchParameter parameter);

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<TwitchSubscriptionData> subscriptToEventSub(@NonNull TwitchSubscriptionRequest request);

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<TwitchSubscriptionData> getEventSubSubscriptions();

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Mono<Nil> deleteEventSubSubscription(@NonNull String id);




    @RequiredToken(TokenType.USER_TOKEN)
    @RequiredScope("user:read:follows")
    Flux<Nil> GetFollowedStreams(@NonNull String userId);

    @RequiredToken(TokenType.USER_TOKEN)
    Flux<Nil> getStreamTags();


}
