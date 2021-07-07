package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredToken;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TwitchService extends TwitchServiceEventSub {

    @RequiredToken(TokenType.CLIENT_TOKEN)
    @NonNull Flux<Game> getGames(@NonNull GameSearchParameter parameter);

}
