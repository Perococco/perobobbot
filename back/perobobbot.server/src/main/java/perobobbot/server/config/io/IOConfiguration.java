package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.DisposableIO;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MutableIO;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.lang.PluginService;
import perobobbot.server.component.MessageGateway;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final MutableIO mutableIO = MutableIO.create();

    @Bean(destroyMethod = "dispose")
    @PluginService(type = IO.class, version = IO.VERSION)
    public DisposableIO io() {
        return mutableIO;
    }


    @Bean
    public ChatPlatformPluginManager chatPlatformPluginManager(@NonNull MessageGateway messageGateway, @EventService @NonNull BotService botService) {
        return new ChatPlatformPluginManager(mutableIO,messageGateway,botService);
    }

}
