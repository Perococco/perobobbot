package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.lang.StandardInputReader;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class MessagingConfiguration {

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(destroyMethod = "requestStop", initMethod = "start")
    public @NonNull StandardInputReader standardInputReader() {
        return new StandardInputReader();
    }

}
