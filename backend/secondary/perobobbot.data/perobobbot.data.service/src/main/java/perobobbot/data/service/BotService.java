package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Bot;

import java.util.Optional;
import java.util.UUID;

public interface BotService {

    /**
     * @param login the login of a user
     * @return the list of bots of the user
     */
    @NonNull ImmutableList<Bot> getBots(@NonNull String login);

    /**
     * @param botId the id of the bot to delete
     */
    void deleteBot(@NonNull UUID botId);

    /**
     * @param login the login of the user that will own the bot
     * @param botName the name of the bot to create
     * @return the created bot information
     */
    @NonNull Bot createBot(@NonNull String login, @NonNull String botName);

    /**
     * @param botId the id of the bot to find
     * @return an optional containing the bot with the provided id if found, an empty optional otherwise
     */
    @NonNull Optional<Bot> findBot(@NonNull UUID botId);

    /**
     * @param login the login of the user
     * @return all the bots for the user with the given login
     */
    @NonNull ImmutableList<Bot> listBots(@NonNull String login);
}
