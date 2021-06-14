package perobobbot.twitch.eventsub.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Packages;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.manager._private.SimpleEventSubManager;

@Configuration
@RequiredArgsConstructor
public class EventSubManagerConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("EventSubManager",EventSubManagerConfiguration.class);
    }

    private final @NonNull TwitchService twitchService;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull ObjectMapper objectMapper;
    private final @Value("${perobobbot.eventsub.secret}") String secret;

    @Bean(destroyMethod = "stop", initMethod = "start")
    public SimpleEventSubManager eventSubManager() {
        return new SimpleEventSubManager(twitchService,webHookManager,objectMapper,secret);
    }
}
