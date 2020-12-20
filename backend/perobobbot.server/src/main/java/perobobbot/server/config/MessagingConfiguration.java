package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;
import perobobbot.lang.StandardInputReader;
import perobobbot.messaging.ChatController;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class MessagingConfiguration {

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(destroyMethod = "stop")
    public @NonNull CommandController commandController(@NonNull IO io, @NonNull ChatController chatController) {
        final CommandController commandController = CommandController.builder(io, chatController::addListener)
                                                                     .setCommandPrefix('!')
                                                                     .build();
        commandController.start();
        return commandController;
    }

    @Bean(destroyMethod = "requestStop",initMethod = "start")
    public @NonNull StandardInputReader standardInputReader() {
        return new StandardInputReader();
    }

}
