package perobobbot.server.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.common.lang.MessageContext;

@Configuration
@EnableIntegration
public class ChatMessaging {

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

    @ServiceActivator(inputChannel = "chatChannel")
    public void trace(@NonNull MessageContext messageContext) {
        System.out.println(messageContext.getContent());
    }


}
