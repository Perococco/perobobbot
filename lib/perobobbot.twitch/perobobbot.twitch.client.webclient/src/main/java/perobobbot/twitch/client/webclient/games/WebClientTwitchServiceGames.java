package perobobbot.twitch.client.webclient.games;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.ClientApiToken;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.twitch.client.api.games.Game;
import perobobbot.twitch.client.api.games.GameSearchParameter;
import perobobbot.twitch.client.api.games.TwitchServiceGamesWithToken;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@RequiredArgsConstructor
public class WebClientTwitchServiceGames implements TwitchServiceGamesWithToken {

    private final @NonNull OAuthWebClientFactory webClientFactory;

    @Override
    public @NonNull Flux<Game> getGames(@NonNull ClientApiToken token, @NonNull GameSearchParameter parameter) {
        final var queryParams = parameter.createQueryParameters();

        return webClientFactory.create(token)
                               .get("/games", queryParams)
                               .retrieve()
                               .bodyToMono(GetGamesResponse.class)
                               .flatMapIterable(g -> Arrays.asList(g.getData()));
    }

}
