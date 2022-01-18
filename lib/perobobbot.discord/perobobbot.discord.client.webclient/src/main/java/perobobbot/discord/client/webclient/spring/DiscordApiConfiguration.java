package perobobbot.discord.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.discord.client.api.DiscordService;
import perobobbot.discord.client.api.DiscordServiceWithToken;
import perobobbot.discord.client.webclient.ProxyDiscordService;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Packages;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.oauth.tools.ServiceWithTokenMapper;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class DiscordApiConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Discord API", DiscordApiConfiguration.class);
    }

    private final @NonNull WebClientFactory webClientFactory;

    private final @NonNull ServiceWithTokenMapper serviceWithTokenMapper;

    @Bean
    @PluginService(type = DiscordService.class, apiVersion = DiscordService.VERSION, sensitive = false)
    public DiscordService discordService() {
        return serviceWithTokenMapper.mapService(Platform.DISCORD, ProxyDiscordService.lastVersion(webClientFactory), DiscordServiceWithToken.class, DiscordService.class);
    }

}
