package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.oauth.SubscriptionData;
import perobobbot.twitch.client.api.Game;
import perobobbot.twitch.client.api.GameSearchParameter;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.IntFunction;

@RestController
@RequestMapping("/api/twitch")
@RequiredArgsConstructor
public class TwitchController {

    private final @NonNull TwitchService twitchService;

    @GetMapping(value = "games", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Game> getGames(@RequestParam(value = "id",required = false) String[] ids, @RequestParam(value = "name",required = false) String[] names) {
        final var parameter = new GameSearchParameter(
                emptyIfNull(ids,String[]::new),
                emptyIfNull(names,String[]::new)
        );

        return twitchService.getGames(parameter);
    }

    @GetMapping(value = "eventsubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TwitchSubscriptionData> getEventSubSubscriptions() {
        return twitchService.getEventSubSubscriptions();
    }

    private <T> @NonNull T[] emptyIfNull(T[] array, IntFunction<T[]> factory) {
        if (array == null) {
            return factory.apply(0);
        }
        return array;
    }
}
