package perococco.perobobbot.rest.client.template;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestOperations;
import perobobbot.lang.Bot;
import perobobbot.lang.Todo;
import perobobbot.rest.client.BotClient;
import perobobbot.rest.com.CreateBotParameters;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateBotClient implements BotClient {

    private final @NonNull AsyncRestOperations restOperations;

    @Override
    public CompletionStage<?> deleteBot(@NonNull UUID id) {
        return restOperations.delete("/bots/{id}", Map.of("id",id.toString()));
    }

    @Override
    public @NonNull CompletionStage<ImmutableList<Bot>> listBots() {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<Bot> createBot(@NonNull CreateBotParameters parameters) {
        return Todo.TODO();
    }
}
