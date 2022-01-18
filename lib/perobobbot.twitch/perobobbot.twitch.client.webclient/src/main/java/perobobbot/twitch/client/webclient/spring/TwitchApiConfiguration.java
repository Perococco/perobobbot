package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Packages;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;
import perobobbot.oauth.tools.ServiceWithTokenMapper;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.client.webclient.ProxyTwitchService;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class TwitchApiConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Twitch API", TwitchApiConfiguration.class);
    }

    private final @NonNull WebClientFactory webClientFactory;

    private final @NonNull ServiceWithTokenMapper serviceWithTokenMapper;

    @Bean
    @PluginService(type = TwitchService.class, apiVersion = TwitchService.VERSION, sensitive = false)
    public TwitchService twitchService() {
        return serviceWithTokenMapper.mapService(Platform.TWITCH, ProxyTwitchService.helix(webClientFactory), TwitchServiceWithToken.class, TwitchService.class);
    }

}
