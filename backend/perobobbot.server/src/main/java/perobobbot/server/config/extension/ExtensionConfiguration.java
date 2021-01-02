package perobobbot.server.config.extension;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;
import perobobbot.extension.ExtensionManagerFactory;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class ExtensionConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull IO io;
    private final @NonNull CommandController commandController;
    private final @NonNull PolicyManager policyManager;

    @Bean(destroyMethod = "disableAll")
    public ExtensionManagerFactory extensionManagerFactory() {
        final var extensionFactories = ExtensionLister.gatherAllExtensions(applicationContext);
        return new ExtensionManagerFactory(io, commandController, policyManager, extensionFactories);
    }


}
