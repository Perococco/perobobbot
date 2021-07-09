package perobobbot.data.security.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.proxy.ProxyBotService;
import perobobbot.lang.Bot;
import perobobbot.lang.JoinedChannel;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;

import java.util.Optional;
import java.util.UUID;

@Service
@SecuredService
@PluginService(type = BotService.class, apiVersion = BotService.VERSION,sensitive = true)
public class SecuredBotService extends ProxyBotService {

    public SecuredBotService(@NonNull @EventService BotService delegate) {
        super(delegate);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public @NonNull ImmutableList<Bot> listAllBots() {
        return super.listAllBots();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotEntity','DELETE')")
    public void deleteBot(@NonNull UUID botId) {
        super.deleteBot(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Bot createBot(@NonNull String login, @NonNull String botName) {
        return super.createBot(login, botName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull Optional<Bot> findBotByName(@NonNull String login, @NonNull String botName) {
        return super.findBotByName(login,botName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotEntity','READ')")
    public @NonNull Optional<Bot> findBot(@NonNull UUID botId) {
        return super.findBot(botId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || authentication.name == #login")
    public @NonNull ImmutableList<Bot> listBots(@NonNull String login) {
        return super.listBots(login);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#botId,'BotEntity','WRITE')" )
    public @NonNull JoinedChannel addJoinedChannel(@NonNull UUID botId, @NonNull UUID viewerIdentityId, @NonNull String channelName) {
        return super.addJoinedChannel(botId, viewerIdentityId, channelName);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#joinedChannelId,'JoinedChannelEntity','DELETE')" )
    public void removeJoinedChannel(@NonNull UUID joinedChannelId) {
        super.removeJoinedChannel(joinedChannelId);
    }

    @Override
    public @NonNull ImmutableList<JoinedChannel> findJoinedChannels(@NonNull Platform platform) {
        return super.findJoinedChannels(platform);
    }
}
