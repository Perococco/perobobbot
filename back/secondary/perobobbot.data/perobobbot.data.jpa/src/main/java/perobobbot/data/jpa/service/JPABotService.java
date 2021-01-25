package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.jpa.repository.BotRepository;
import perobobbot.data.jpa.repository.CredentialRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.BotService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Bot;

import java.util.Optional;
import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPABotService implements BotService {

    @NonNull
    private final BotRepository botRepository;

    @NonNull
    private final CredentialRepository credentialRepository;

    @NonNull
    private final UserRepository userRepository;

    @Override
    public @NonNull ImmutableList<Bot> getBots(@NonNull String login) {
        final var user = userRepository.getByLogin(login);
        return user.getBots().stream().map(BotEntity::toView).collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull Optional<Bot> findBot(@NonNull UUID botId) {
        return botRepository.findByUuid(botId).map(BotEntity::toView);
    }

    @Override
    public @NonNull Optional<Bot> findBotByName(@NonNull String login, @NonNull String botName) {
        return botRepository.findByNameAndOwnerLogin(botName,login).map(BotEntity::toView);
    }

    @Override
    @Transactional
    public @NonNull void attachCredential(@NonNull UUID botId, @NonNull UUID credentialId) {
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
}
