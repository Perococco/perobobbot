package perobobbot.server.config.io;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.lang.GatewayChannels;
import perobobbot.lang.PluginService;
import perobobbot.lang.StandardInputProvider;
import perobobbot.lang.StandardInputReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//TODO move to a more adapted package
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
        return new PublishSubscribeChannel(messagingExecutor());
    }

    @Bean(destroyMethod = "requestStop", initMethod = "start")
    @PluginService(type = StandardInputProvider.class, apiVersion = StandardInputProvider.VERSION)
    public @NonNull StandardInputReader standardInputReader() {
        return new StandardInputReader();
    }

}
