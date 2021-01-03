package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.StandardInputReader;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableIntegration
public class MessagingConfiguration {

    @Bean(destroyMethod = "shutdown")
    @Qualifier("messaging")
    public @NonNull ExecutorService messagingExecutor() {
        return Executors.newCachedThreadPool();
    }


    @Bean(GatewayChannels.PLATFORM_MESSAGES)
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel(messagingExecutor());
    }

    @Bean(GatewayChannels.EVENT_MESSAGES)
    public MessageChannel eventChannel() {
        final PublishSubscribeChannel channel = new PublishSubscribeChannel(messagingExecutor());
        return channel;
    }

    @Bean(destroyMethod = "requestStop", initMethod = "start")
    public @NonNull StandardInputReader standardInputReader() {
        return new StandardInputReader();
    }

}
