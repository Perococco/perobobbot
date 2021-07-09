package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.DisposableIO;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MutableIO;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.PluginService;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final MutableIO mutableIO = MutableIO.create();

    @Bean(destroyMethod = "dispose")
    @PluginService(type = IO.class, apiVersion = IO.VERSION, sensitive = false)
    public DisposableIO io() {
        return mutableIO;
    }


    @Bean
    public ChatPlatformPluginManager chatPlatformPluginManager(
            @NonNull MessageGateway messageGateway,
            @NonNull Rejoiner rejoiner) {
        return new ChatPlatformPluginManager(mutableIO,messageGateway,rejoiner);
    }

}
