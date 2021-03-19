package perobobbot.server.config.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;
import perobobbot.command.CommandExecutor;
import perobobbot.command.CommandRegistry;
import perobobbot.messaging.ChatController;
import perobobbot.server.config.Orders;

@Configuration
@RequiredArgsConstructor
public class CommandConfiguration {

    @NonNull
    private final IO io;

    @NonNull
    private final ChatController chatController;

    @NonNull
    @Qualifier("with-access-rule")
    private final CommandExecutor commandExecutor;


    @Bean(destroyMethod = "stop")
    public @NonNull CommandController commandController() {
        final CommandController commandController = CommandController.builder(chatController, commandExecutor)
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

}
