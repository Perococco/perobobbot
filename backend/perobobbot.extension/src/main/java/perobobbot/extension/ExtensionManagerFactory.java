package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandController;
import perobobbot.extension._private.PeroExtensionManager;
import perobobbot.extension._private.action.*;
import perobobbot.lang.*;

import java.time.Duration;
import java.util.*;

@RequiredArgsConstructor
public class ExtensionManagerFactory {

    /**
     * The IO needed by the commands of the extension manager
     */
    private final @NonNull IO io;

    /**
     * the command controller to which the command registry of the extension manager will be added
     */
    private final @NonNull CommandController commandController;

    /**
     * the policy manager used by the command of the extension
     */
    private final @NonNull PolicyManager policyManager;

    /**
     * All the extension factories found by Spring
     */
    private final @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories;

    /**
     * the created extension managers wrap in a {@link Value}
     */
    private final Map<Bot, Value> extensionManagers = new HashMap<>();

    @Synchronized
    public @NonNull ExtensionManager create(@NonNull Bot bot) {
        if (extensionManagers.containsKey(bot)) {
            throw new PerobobbotException("An extension manager for the bot '" + bot + "' exists already");
        }
        final var extensionManager = PeroExtensionManager.create(bot, extensionFactories, this::createCommandBundleFactory);
        final var subscription = commandController.addCommandRegistry(bot,extensionManager);

        extensionManagers.put(bot, new Value(subscription,extensionManager));
        return extensionManager;
    }

    @Synchronized
    public @NonNull void delete(@NonNull Bot bot) {
        final var value = extensionManagers.remove(bot);
        if (value != null) {
            value.dispose();
        }
    }

    @Synchronized
    public void disableAll() {
        extensionManagers.values().forEach(Value::dispose);
        final Set<Bot> bots = new HashSet<>(extensionManagers.keySet());
        bots.forEach(this::delete);
    }

    private @NonNull CommandBundle createCommandBundleFactory(@NonNull ExtensionManager extensionManager) {
        final var policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        return CommandBundle.builder()
                            .add("em list", policy, new ListExtensions(extensionManager, io))
                            .add("em enable", policy, new EnableExtension(extensionManager))
                            .add("em disable", policy, new DisableExtension(extensionManager))
                            .add("em enable-all", policy, new EnableAllExtensions(extensionManager))
                            .add("em disable-all", policy, new DisableAllExtensions(extensionManager))
                            .build();

    }

    private static class Value {

        private final @NonNull SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

        private final @NonNull ExtensionManager extensionManager;

        public Value(@NonNull Subscription subscription, @NonNull ExtensionManager extensionManager) {
            this.subscriptionHolder.replaceWith(() -> subscription);
            this.extensionManager = extensionManager;
        }

        public void dispose() {
            subscriptionHolder.unsubscribe();
            extensionManager.disableAll();
        }
    }

}
