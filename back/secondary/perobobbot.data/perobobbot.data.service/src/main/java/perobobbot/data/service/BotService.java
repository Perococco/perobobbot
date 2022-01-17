package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.lang.JoinedChannel;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformBot;

import java.util.Optional;
import java.util.UUID;

public interface BotService {

    int VERSION = 1;

    @NonNull ImmutableList<Bot> listAllBots();


    @NonNull PlatformBot createPlatformBot(@NonNull UUID botId, @NonNull UUID platformUserId);

        /**
         * @param login the login of the user
         * @return all the bots for the user with the given login
         */
    @NonNull ImmutableList<Bot> listBots(@NonNull String login);

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

    @NonNull Optional<Bot> findBotByName(@NonNull String login, @NonNull String botName);

    @NonNull ImmutableList<PlatformBot> listPlatformBotsForBotName(@NonNull String login, @NonNull String botName);

    @NonNull JoinedChannel addJoinedChannel(@NonNull UUID platformBotId, @NonNull String channelId);

    @NonNull Optional<JoinedChannel> findJoinedChannel(@NonNull UUID joinedChannelId);

    void removeJoinedChannel(@NonNull UUID joinedChannelId);

    /**
     * @param botId the id of the bot to find
     * @return an optional containing the bot with the provided id if found, an empty optional otherwise
     */
    @NonNull Optional<Bot> findBot(@NonNull UUID botId);

    @NonNull Optional<Bot> findBotOwningPlatformBot(@NonNull UUID platformBotId);

    default @NonNull Optional<String> findLoginOfBotOwner(@NonNull UUID botId) {
        return findBot(botId).map(Bot::getOwnerLogin);
    }

    void enableExtension(@NonNull UUID botId, @NonNull String extensionName);

    @NonNull ImmutableList<JoinedChannel> findJoinedChannels(@NonNull Platform platform);

}
