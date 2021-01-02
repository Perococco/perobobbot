package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;
import perobobbot.command.CommandExecutor;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.StandardInputReader;
import perobobbot.lang.Todo;
import perobobbot.messaging.ChatController;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class MessagingConfiguration {

    @NonNull
    private final IO io;

    @NonNull
    private final ChatController chatController;

    @NonNull
    private final CommandExecutor commandExecutor;

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(destroyMethod = "stop")
    public @NonNull CommandController commandController() {
        final CommandController commandController = CommandController.builder(io, chatController, commandExecutor)
                                                                     .commandRegistry(commandRegistry())
                                                                     .setCommandPrefix('!')
                                                                     .build();
        commandController.start();
        return commandController;
    }

    @Bean
    public @NonNull CommandRegistry commandRegistry() {
        return CommandRegistry.create();
    }

    @Bean(destroyMethod = "requestStop", initMethod = "start")
    public @NonNull StandardInputReader standardInputReader() {
        return new StandardInputReader();
    }

}
