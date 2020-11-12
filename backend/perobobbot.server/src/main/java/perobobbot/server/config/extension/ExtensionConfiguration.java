package perobobbot.server.config.extension;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.command.Command;
import perobobbot.command.CommandBundle;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.IO;
import perobobbot.lang.Role;
import perobobbot.server.config.extension.executor.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class ExtensionConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean(destroyMethod = "disableAll")
    public ExtensionManager extensionManager() {
        final var ext = applicationContext.getBeansOfType(Extension.class);

        final var extensionsByName = ext.values().stream().collect(Collectors.groupingBy(e -> e.getName()));

        final var builder = ImmutableMap.<String,Extension>builder();
        for (Map.Entry<String, List<Extension>> entry : extensionsByName.entrySet()) {
            final var name = entry.getKey();
            final var extensions = entry.getValue();
            if (extensions.size() != 1) {
                LOG.warn("Duplicate extension with name '{}' : {}", name, extensions);
            }
            builder.put(name, extensions.get(0));
        }
        return ExtensionManager.create(builder.build());
    }

    @Bean(name = "extension-manager")
    public CommandBundle commandBundle(@NonNull ExtensionManager extensionManager, @NonNull IO io, @NonNull PolicyManager policyManager) {
        final var policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        return Command.factory()
                .add("em list", policy, new ListExtensions(extensionManager,io))
                .add("em enable", policy, new EnableExtension(extensionManager))
                .add("em disable", policy, new DisableExtension(extensionManager))
                .add("em enable-all", policy, new EnableAllExtensions(extensionManager))
                .add("em disable-all", policy, new DisableAllExtensions(extensionManager))
                .build();
    }



}
