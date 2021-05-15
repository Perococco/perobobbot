package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Todo;
import perobobbot.twitch.client.api.TwitchService;

@RequiredArgsConstructor
public class WebClientAppTwitchService implements TwitchService {

    private final @NonNull WebClient webClient;

    @Override
    public void getStreamTags() {
        Todo.TODO();
    }

    @Override
    public void createEventSubSubscription() {
        Todo.TODO();
    }

    @Override
    public void deleteEventSubSubscription() {
        Todo.TODO();
    }

    @Override
    public void getEventSubSubscriptions() {
        Todo.TODO();
    }
}
