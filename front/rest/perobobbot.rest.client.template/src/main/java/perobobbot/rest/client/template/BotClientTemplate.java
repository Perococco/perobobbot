package perobobbot.rest.client.template;

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

@RequiredArgsConstructor
public class BotClientTemplate implements BotClient {

    private final @NonNull RestOperations restOperations;

    @Override
    public void deleteBot(@NonNull UUID id) {
        restOperations.delete("/bots/{id}", Map.of("id",id.toString()));
    }

    @Override
    public @NonNull ImmutableList<Bot> listBots() {
        return Todo.TODO();
    }

    @Override
    public @NonNull Bot createBot(@NonNull CreateBotParameters parameters) {
        return Todo.TODO();
    }
}
