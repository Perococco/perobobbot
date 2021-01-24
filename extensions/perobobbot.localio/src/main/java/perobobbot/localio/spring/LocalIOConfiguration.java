package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.StandardInputProvider;
import perobobbot.localio.LocalChatPlatform;

@Configuration
@RequiredArgsConstructor
public class LocalIOConfiguration {

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull StandardInputProvider standardInputProvider;
    private final @NonNull @EventService
    ExtensionService extensionService;
    private final @NonNull @EventService
    BotService botService;

    @Bean(destroyMethod = "dispose")
    public LocalChatPlatform console() {
        return new LocalChatPlatform(applicationCloser,
                                     botService,
                                     standardInputProvider);
    }

}
