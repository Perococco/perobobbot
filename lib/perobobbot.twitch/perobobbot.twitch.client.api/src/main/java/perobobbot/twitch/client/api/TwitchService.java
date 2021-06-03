package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredScope;
import perobobbot.oauth.RequiredToken;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TwitchService {

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull String getGames(@NonNull GameSearchParameter parameter);


    @RequiredToken(TokenType.USER_TOKEN)
    @RequiredScope("user:read:follows")
    Flux<Nil> GetFollowedStreams(@NonNull String userId);

    @RequiredToken(TokenType.USER_TOKEN)
    Flux<Nil> getStreamTags();

    @RequiredToken(TokenType.CLIENT_TOKEN)
    Mono<Nil> createEventSubSubscription();



    @RequiredToken(TokenType.CLIENT_TOKEN)
    Mono<Nil> deleteEventSubSubscription();

    @RequiredToken(TokenType.CLIENT_TOKEN)
    Mono<Nil> getEventSubSubscriptions();

}
