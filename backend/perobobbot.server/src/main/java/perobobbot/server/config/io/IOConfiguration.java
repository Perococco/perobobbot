package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.IOBuilder;
import perobobbot.lang.MessageContext;
import perobobbot.server.component.MessageGateway;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull ApplicationContext context;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean
    public IO io() {
        final Map<String, ChatPlatform> platformIOs = context.getBeansOfType(ChatPlatform.class);

        final IOBuilder builder = IO.builder();

        platformIOs.values()
                   .forEach(pio -> {
                       pio.addMessageListener(messageGateway::sendMessage);
                       builder.add(pio.getPlatform(),pio);
                   });

        return builder.build();
    }

    private void localListener(@NonNull MessageContext messageContext) {
        messageGateway.sendMessage(messageContext);
    }

}
