package perobobbot.twutch.eventsub.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Packages;
import perobobbot.twitch.client.api.TwitchService;

@Configuration
@RequiredArgsConstructor
public class EventSubManagerConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("EventSubManager",EventSubManagerConfiguration.class);
    }

    private final @NonNull TwitchService twitchService;
    private final @NonNull WebHookManager webHookManager;
    private final @NonNull ObjectMapper objectMapper;

    @Bean(destroyMethod = "stop", initMethod = "start")
    public SimpleEventSubManager eventSubManager() {
        return new SimpleEventSubManager(twitchService,webHookManager,objectMapper);
    }
}
