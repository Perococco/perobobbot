package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.jpa.repository.BotRepository;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;
import perobobbot.lang.Todo;

import java.util.UUID;

@Service
@Transactional()
@RequiredArgsConstructor
public class JPABotService implements BotService {

    @NonNull
    private final BotRepository botRepository;

    @NonNull
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR') || authentication.name == #login")
    public @NonNull ImmutableList<Bot> getBots(@NonNull String login) {
        final var user = userRepository.getByLogin(login);
        return user.getBots().stream().map(BotEntity::toView).collect(ImmutableList.toImmutableList());
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR') || hasPermission(#targetId,'BotEntity','DELETE')")
    public void deleteBot(@NonNull UUID botId) {
        botRepository.deleteByUuid(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR') || authentication.name == #login")
    public @NonNull Bot createBot(@NonNull String login, @NonNull String botName) {
        final var user = userRepository.getByLogin(login);
        final var bot = user.createBot(botName);
        return botRepository.save(bot).toView();
    }
}
