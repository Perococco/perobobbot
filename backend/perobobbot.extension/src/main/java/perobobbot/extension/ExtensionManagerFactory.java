package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionManager;
import perobobbot.extension._private.PeroExtensionManager;
import perobobbot.extension._private.action.*;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Role;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ExtensionManagerFactory {

    private final @NonNull IO io;

    private final @NonNull PolicyManager policyManager;

    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories;

    private final Map<String, ExtensionManager> extensionManagers = new HashMap<>();

    @Synchronized
    public @NonNull ExtensionManager create(@NonNull String userId) {
        if (extensionManagers.containsKey(userId)) {
            throw new PerobobbotException("An extension manager for the user '" + userId + "' exists already");
        }
        final var extensionManager = new PeroExtensionManager(userId, extensionFactories, this::createCommandBundleLifeCycle);
        extensionManagers.put(userId, extensionManager);
        return extensionManager;
    }

    @Synchronized
    public void disableAll() {
        extensionManagers.forEach((u, e) -> e.disableAll());
        extensionManagers.clear();
    }

    private @NonNull CommandBundleLifeCycle createCommandBundleLifeCycle(@NonNull ExtensionManager extensionManager) {
        final var policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        return CommandBundle.builder()
                            .add("em list", policy, new ListExtensions(extensionManager, io))
                            .add("em enable", policy, new EnableExtension(extensionManager))
                            .add("em disable", policy, new DisableExtension(extensionManager))
                            .add("em enable-all", policy, new EnableAllExtensions(extensionManager))
                            .add("em disable-all", policy, new DisableAllExtensions(extensionManager))
                            .build()
                            .createLifeCycle(commandRegistry);

    }

}
