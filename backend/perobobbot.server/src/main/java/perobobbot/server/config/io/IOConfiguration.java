package perobobbot.server.config.io;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.*;
import perobobbot.server.component.MessageGateway;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull ApplicationContext context;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean
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

    private void localListener(@NonNull MessageContext messageContext) {
        System.out.println(messageContext.getContent());
        messageGateway.sendMessage(messageContext);
    }

}
