package perobobbot.twitch.client.api.games;

import lombok.NonNull;
import perobobbot.oauth.ClientApiToken;
import reactor.core.publisher.Flux;

public interface TwitchServiceGamesWithToken {

    @NonNull Flux<Game> getGames(@NonNull ClientApiToken token, @NonNull GameSearchParameter parameter);

}
