package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Bot;

import java.util.UUID;

public interface BotService {

    @NonNull ImmutableList<Bot> getBots(@NonNull String userLogin);

    void deleteBot(@NonNull UUID botId);

    @NonNull Bot createBot(@NonNull String login, @NonNull String botName);


}
