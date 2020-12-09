package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionManagerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class ExtensionConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    private final @NonNull IO io;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull CommandRegistry commandRegistry;

    @Bean(destroyMethod = "disableAll")
    public ExtensionManagerFactory extensionManagerFactory() {
        final var ext = applicationContext.getBeansOfType(ExtensionFactory.class);

        final var extensionsByName = ext.values()
                                        .stream()
                                        .collect(Collectors.groupingBy(ExtensionFactory::getExtensionName));

        final var builder = ImmutableMap.<String,ExtensionFactory>builder();
        for (Map.Entry<String, List<ExtensionFactory>> entry : extensionsByName.entrySet()) {
            final var name = entry.getKey();
            final var extensions = entry.getValue();
            if (extensions.size() != 1) {
                LOG.warn("Duplicate extension with name '{}' : {}", name, extensions);
            }
            builder.put(name, extensions.get(0));
        }
        return new ExtensionManagerFactory(io,policyManager,commandRegistry,builder.build());
    }




}
