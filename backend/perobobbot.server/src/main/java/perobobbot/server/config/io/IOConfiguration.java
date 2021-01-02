package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.DisposableIO;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.IOBuilder;
import perobobbot.server.component.MessageGateway;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull ApplicationContext context;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean(destroyMethod = "dispose")
    public DisposableIO io() {
        final Map<String, ChatPlatform> platformIOs = context.getBeansOfType(ChatPlatform.class);

        final IOBuilder builder = IO.builder();

        platformIOs.values()
                   .forEach(pio -> {
                       pio.addMessageListener(messageGateway::sendPlatformMessage);
                       builder.add(pio.getPlatform(),pio);
                   });

        return builder.build();
    }

}
