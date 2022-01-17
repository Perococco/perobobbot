package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.proxy.ProxyBotService;
import perobobbot.lang.*;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@RequiredArgsConstructor
@PluginService(type = BotService.class, apiVersion = BotService.VERSION,sensitive = true)
public class SecuredBotService implements BotService {

    private final @NonNull @EventService BotService delegate;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<Bot> listAllBots() {
        return delegate.listAllBots();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotEntity','DELETE')")
    public void deleteBot(@NonNull UUID botId) {
        delegate.deleteBot(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Bot createBot(@NonNull String login, @NonNull String botName) {
        return delegate.createBot(login, botName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<Bot> findBotByName(@NonNull String login, @NonNull String botName) {
        return delegate.findBotByName(login,botName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotEntity','READ')")
    public @NonNull Optional<Bot> findBot(@NonNull UUID botId) {
        return delegate.findBot(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableList<Bot> listBots(@NonNull String login) {
        return delegate.listBots(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#platformBotId,'PlatformBotEntity','WRITE')" )
    public @NonNull JoinedChannel addJoinedChannel(@NonNull UUID platformBotId, @NonNull String channelName) {
        return delegate.addJoinedChannel(platformBotId, channelName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#joinedChannelId,'JoinedChannelEntity','DELETE')" )
    public void removeJoinedChannel(@NonNull UUID joinedChannelId) {
        delegate.removeJoinedChannel(joinedChannelId);
    }

    @Override
    public @NonNull ImmutableList<JoinedChannel> findJoinedChannels(@NonNull Platform platform) {
        return delegate.findJoinedChannels(platform);
    }

    @Override
    public @NonNull PlatformBot createPlatformBot(@NonNull UUID botId, @NonNull UUID platformUserId) {
        return delegate.createPlatformBot(botId, platformUserId);
    }

    @Override
    @NonNull
    public ImmutableList<PlatformBot> listPlatformBotsForBotName(@NonNull String login, @NonNull String botName) {
        return delegate.listPlatformBotsForBotName(login, botName);
    }

    @Override
    @NonNull
    public Optional<JoinedChannel> findJoinedChannel(@NonNull UUID joinedChannelId) {
        return delegate.findJoinedChannel(joinedChannelId);
    }

    @Override
    @NonNull
    public Optional<Bot> findBotOwningPlatformBot(@NonNull UUID platformBotId) {
        return delegate.findBotOwningPlatformBot(platformBotId);
    }

    @Override
    @NonNull
    public Optional<String> findLoginOfBotOwner(@NonNull UUID botId) {
        return delegate.findLoginOfBotOwner(botId);
    }

    @Override
    public void enableExtension(@NonNull UUID botId, @NonNull String extensionName) {
        delegate.enableExtension(botId, extensionName);
    }
}
