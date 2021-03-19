package perobobbot.server.plugin.extension;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.ExtensionManager;

@Configuration
@Log4j2
public class ExtensionConfiguration {

    @Bean
    public @NonNull ExtensionManager extensionManager(@NonNull CommandRegistry commandRegistry) {
        return ExtensionManager.create(commandRegistry);
    }

}
