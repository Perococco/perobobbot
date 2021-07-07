package perobobbot.twitch.client.api.games;

import lombok.NonNull;
import perobobbot.oauth.ClientOAuth;
import reactor.core.publisher.Flux;

public interface TwitchServiceGames {

    @ClientOAuth
    @NonNull Flux<Game> getGames(@NonNull GameSearchParameter parameter);

}
