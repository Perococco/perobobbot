package perobobbot.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class ChatMessaging {

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

}
