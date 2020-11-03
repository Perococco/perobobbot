package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.IOBuilder;
import perobobbot.common.lang.PlatformIO;
import perobobbot.server.config.MessageGateway;
import perobobbot.server.config.Service;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull ApplicationContext context;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean
    @Service
    public IO io() {
        final Map<String,PlatformIO> platformIOs = context.getBeansOfType(PlatformIO.class);

        final IOBuilder builder = IO.builder();

        platformIOs.values()
                   .forEach(pio -> {
                       pio.addMessageListener(messageGateway::sendMessage);
                       builder.add(pio.getPlatform(),pio);
                   });

        return builder.build();
    }


}
