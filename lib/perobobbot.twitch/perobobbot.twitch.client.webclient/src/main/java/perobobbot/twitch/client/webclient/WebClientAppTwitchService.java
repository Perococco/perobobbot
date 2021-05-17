package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Nil;
import perobobbot.lang.Todo;
import perobobbot.twitch.client.api.TwitchService;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class WebClientAppTwitchService implements TwitchService {

    private final @NonNull WebClient webClient;

    @Override
    public CompletionStage<Nil> getStreamTags() {
        return Todo.TODO();
    }

    @Override
    public CompletionStage<Nil> createEventSubSubscription() {
        return Todo.TODO();
    }

    @Override
    public CompletionStage<Nil> deleteEventSubSubscription() {
        return Todo.TODO();
    }

    @Override
    public CompletionStage<Nil> getEventSubSubscriptions() {
        return Todo.TODO();
    }
}
