package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.rest.com.CreateBotParameters;

import java.util.UUID;

public interface BotClient {

    void deleteBot(@NonNull UUID id);

    @NonNull ImmutableList<Bot> listBots();

    @NonNull Bot createBot(@NonNull CreateBotParameters parameters);

}
