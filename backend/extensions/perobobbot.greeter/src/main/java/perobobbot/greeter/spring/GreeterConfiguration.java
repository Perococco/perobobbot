package perobobbot.greeter.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.greeter.GreeterExtension;
import perobobbot.lang.IO;
import perobobbot.messaging.ChatController;

@Configuration
@RequiredArgsConstructor
public class GreeterConfiguration {

    private final @NonNull IO io;

    private final @NonNull ChatController chatController;

    @Bean
    public GreeterExtension greeter() {
        return new GreeterExtension(io,chatController);
    }
}
