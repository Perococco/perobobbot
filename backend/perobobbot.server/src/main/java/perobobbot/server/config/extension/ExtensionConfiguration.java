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
import perobobbot.command.CommandController;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionManagerFactory;
import perobobbot.lang.MapTool;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        final var extensionFactories = gatherAllExtensionFactories();
        return new ExtensionManagerFactory(io, commandController, policyManager, extensionFactories);
    }

    private @NonNull ImmutableMap<String, ExtensionFactory> gatherAllExtensionFactories() {
        final var factoriesByName = applicationContext.getBeansOfType(ExtensionFactory.class)
                                                      .values()
                                                      .stream()
                                                      .collect(Collectors.groupingBy(ExtensionFactory::getExtensionName));

        return factoriesByName.entrySet()
                              .stream()
                              .map(this::selectOneExtension)
                              .flatMap(Optional::stream)
                              .collect(MapTool.collector(ExtensionFactory::getExtensionName));
    }

    private @NonNull Optional<ExtensionFactory> selectOneExtension(@NonNull Map.Entry<String, List<ExtensionFactory>> entry) {
        final var name = entry.getKey();
        final var list = entry.getValue();
        if (list.isEmpty()) {
            LOG.error("No extension with the name '{}' : this is a bug", name);
            return Optional.empty();
        }
        if (list.size() > 1) {
            LOG.warn("Duplicate extension with name '{}' : {}", name, list);
        }
        return Optional.ofNullable(list.get(0));
    }

}
