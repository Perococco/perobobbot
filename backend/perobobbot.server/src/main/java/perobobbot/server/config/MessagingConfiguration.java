package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import perobobbot.common.command.CommandBundle;
import perobobbot.common.command.CommandController;
import perobobbot.common.command.CommandRegistry;
import perobobbot.common.messaging.ChatController;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class MessagingConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public MessageChannel chatChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public @NonNull CommandRegistry commandRegistry() {
        final CommandRegistry commandRegistry = CommandRegistry.create();
        final var commandBundles = applicationContext.getBeansOfType(CommandBundle.class);
        commandBundles.values().stream().forEach(b -> b.attachTo(commandRegistry));
        return commandRegistry;
    }

    @Bean
    public @NonNull CommandController commandController(@NonNull CommandRegistry commandRegistry, @NonNull ChatController chatController) {
        final CommandController commandController = CommandController.builder()
                                                                     .setCommandPrefix('!')
                                                                     .setCommandRegistry(commandRegistry)
                                                                     .build();
        chatController.addListener(commandController);

        return commandController;
    }


}
