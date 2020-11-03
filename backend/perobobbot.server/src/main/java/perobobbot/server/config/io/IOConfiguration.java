package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.lang.*;
import perobobbot.server.config.MessageGateway;
import perobobbot.server.config.Service;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatIO;
import perobobbot.twitch.chat.TwitchChatOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Log4j2
public class IOConfiguration {

    private final @NonNull ApplicationContext context;

    @Bean
    @Service
    public IO io() {
        final Map<String,PlatformIO> platformIOs = context.getBeansOfType(PlatformIO.class);

        final IOBuilder builder = IO.builder();

        platformIOs.values()
                   .forEach(pio -> builder.add(pio.getPlatform(),pio));

        return builder.build();
    }


}
