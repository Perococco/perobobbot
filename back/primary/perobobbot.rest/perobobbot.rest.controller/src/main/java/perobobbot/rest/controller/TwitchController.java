package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import perobobbot.twitch.eventsub.api.EventSubManager;
import perobobbot.twitch.eventsub.api.TwitchSubscription;
import perobobbot.twitch.eventsub.api.TwitchSubscriptionData;
import perobobbot.twitch.eventsub.api.condition.ChannelUpdate;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/twitch")
@RequiredArgsConstructor
public class TwitchController {

    private final @NonNull EventSubManager eventSubManager;

    @GetMapping(value = "eventsubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TwitchSubscriptionData> getEventSubSubscriptions() {
        return eventSubManager.listSubscriptions();
    }

    @PostMapping(value = "eventsubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TwitchSubscription> subscribe() {
        return eventSubManager.subscribe(new ChannelUpdate("211307900"));
    }

    @DeleteMapping(value = "eventsubs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> subscribe(@NonNull @PathVariable(name = "id") String id) {
        return eventSubManager.deleteSubscription(id).map(o -> id);
    }

}
