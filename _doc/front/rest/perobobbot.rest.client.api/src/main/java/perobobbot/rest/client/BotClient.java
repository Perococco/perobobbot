package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.rest.com.CreateBotParameters;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface BotClient {

    @NonNull CompletionStage<ImmutableList<Bot>> listBots();

    @NonNull CompletionStage<?> deleteBot(@NonNull UUID id);

    @NonNull CompletionStage<Bot> createBot(@NonNull CreateBotParameters parameters);

}
