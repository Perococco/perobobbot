package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.eventsub.EventSubManager;
import perobobbot.eventsub.PlatformEventSubManager;

@Configuration
@RequiredArgsConstructor
public class EventSubConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public @NonNull EventSubManager eventSubManager() {
        final var platformEventSubManagers = ImmutableList.copyOf(applicationContext.getBeansOfType(PlatformEventSubManager.class).values());
        return MapBaseEventSubManager.create(platformEventSubManagers);
    }

}
