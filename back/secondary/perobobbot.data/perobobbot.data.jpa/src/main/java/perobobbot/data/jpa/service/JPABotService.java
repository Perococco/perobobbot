package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.BotExtensionEntity;
import perobobbot.data.domain.JoinedChannelEntity;
import perobobbot.data.jpa.repository.*;
import perobobbot.data.service.BotService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPABotService implements BotService {

    private final @NonNull BotRepository botRepository;

    private final @NonNull ExtensionRepository extensionRepository;

    private final @NonNull BotExtensionRepository botExtensionRepository;

    private final @NonNull CredentialRepository credentialRepository;

    private final @NonNull UserRepository userRepository;

    private final @NonNull JoinedChannelRepository joinedChannelRepository;

    @Override
    public @NonNull ImmutableList<Bot> getBots(@NonNull String login) {
        final var user = userRepository.getByLogin(login);
        return user.getBots().stream().map(BotEntity::toView).collect(ImmutableList.toImmutableList());
    }

    @Override
    @Transactional
    public void enableExtension(@NonNull UUID botId, @NonNull String extensionName) {
        final var botExtension = botExtensionRepository.findByBot_UuidAndExtension_Name(botId, extensionName)
                                                       .orElseGet(() -> createBotExtension(botId, extensionName));

        botExtension.setEnabled(true);

        botExtensionRepository.save(botExtension);
    }

    private @NonNull BotExtensionEntity createBotExtension(@NonNull UUID botId, @NonNull String extensionName) {
        final var bot = botRepository.getByUuid(botId);
        final var extension = extensionRepository.getByName(extensionName);
        return bot.addExtension(extension);
    }

    @Override
    public @NonNull Optional<Bot> findBot(@NonNull UUID botId) {
        return botRepository.findByUuid(botId).map(BotEntity::toView);
    }

    @Override
    public @NonNull Optional<Bot> findBotByName(@NonNull String login, @NonNull String botName) {
        return botRepository.findByNameAndOwnerLogin(botName, login).map(BotEntity::toView);
    }

    @Override
    @Transactional
    public void attachCredential(@NonNull UUID botId, @NonNull UUID credentialId) {
        final var bot = botRepository.getByUuid(botId);
        final var credential = credentialRepository.getByUuid(credentialId);
        bot.attachCredential(credential);

        botRepository.save(bot);
    }

    @Override
    @Transactional
    public void deleteBot(@NonNull UUID botId) {
        botRepository.deleteByUuid(botId);
    }

    @Override
    @Transactional
    public @NonNull Bot createBot(@NonNull String login, @NonNull String botName) {
        final var user = userRepository.getByLogin(login);
        final var bot = user.createBot(botName);
        return botRepository.save(bot).toView();
    }

    @Override
    public @NonNull ImmutableList<Bot> listBots(@NonNull String login) {
        return botRepository.findAllByOwnerLogin(login)
                            .map(BotEntity::toView)
                            .collect(ImmutableList.toImmutableList());
    }


    @Override
    @Transactional
    public void saveChannelConnection(@NonNull UUID botId, @NonNull Platform platform, @NonNull String channelName) {
        final var bot = botRepository.getByUuid(botId);

        final var existing = joinedChannelRepository.findByBotAndPlatformAndChannelName(bot,platform,channelName);
        if (existing.isPresent()) {
            return;
        }
        final var joinedChannelEntity = bot.createJoinedChannel(platform,channelName);
        joinedChannelRepository.save(joinedChannelEntity);
    }

    @Override
    public @NonNull ImmutableList<JoinedChannel> findConnections(@NonNull Platform platform) {
        return joinedChannelRepository.findByPlatform(platform)
                                      .map(JoinedChannelEntity::toView)
                                      .collect(ImmutableList.toImmutableList());
    }
}
