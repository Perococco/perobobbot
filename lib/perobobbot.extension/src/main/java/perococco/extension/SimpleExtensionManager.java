package perococco.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ExtensionPlugin;

import java.util.*;

@RequiredArgsConstructor
@Log4j2
public class SimpleExtensionManager implements ExtensionManager {

    private final @NonNull CommandRegistry commandRegistry;

    private @NonNull ImmutableSet<String> enabledExtensions = ImmutableSet.of();
    private final @NonNull Map<UUID, Subscription> subscriptions = new HashMap<>();

    private final @NonNull Map<String, UUID> extensionIds = new HashMap<>();
    private final @NonNull Map<UUID, ExtensionPlugin> extensionPlugins = new HashMap<>();

    @Override
    @Synchronized
    public @NonNull Optional<Subscription> addExtension(@NonNull ExtensionPlugin extensionPlugin) {
        final String extensionName = extensionPlugin.getExtensionName();
        if (extensionIds.containsKey(extensionName)) {
            LOG.warn("An extension with the name '{}' is registered already", extensionName);
            return Optional.empty();
        }
        final UUID id = UUID.randomUUID();
        this.extensionIds.put(extensionName, id);
        this.extensionPlugins.put(id, extensionPlugin);

        final var subscription = this.commandRegistry.addCommandDefinitions(extensionPlugin.getCommandDefinitions());

        extensionPlugin.getExtension().enable();

        return Optional.of(Subscription.multi(
                subscription,
                () -> removeExtension(id)
        ));
    }

    @Synchronized
    private void removeExtension(@NonNull UUID id) {
        final var subscription = subscriptions.remove(id);
        if (subscription != null) {
            subscription.unsubscribe();
        }
        final var extensionPlugin = extensionPlugins.remove(id);
        if (extensionPlugin != null) {
            this.extensionIds.remove(extensionPlugin.getExtensionName());
        }
    }

    @Synchronized
    public void enableExtensions(@NonNull ImmutableSet<String> enabledExtensions) {
        this.enabledExtensions = enabledExtensions;
        this.synchronizeStates();
    }

    private void synchronizeStates() {
        final var currentlyEnabled = ImmutableSet.copyOf(subscriptions.keySet());
        final var toEnable = getEnabledExtensionIds();

        currentlyEnabled.stream()
                        .filter(i -> !toEnable.contains(i))
                        .forEach(this::disable);

        toEnable.stream()
                .filter(i -> !subscriptions.containsKey(i))
                .forEach(this::enable);

    }


    public void disable(@NonNull UUID id) {
        Optional.ofNullable(this.subscriptions.remove(id))
                .ifPresent(Subscription::unsubscribe);
    }

    public void enable(@NonNull UUID id) {
        this.disable(id);
        Optional.ofNullable(extensionPlugins.get(id))
                .map(e -> Subscription.multi(
                        e::disableExtension,
                        commandRegistry.addCommandDefinitions(e.getCommandDefinitions())
                ))
                .ifPresent(s -> this.subscriptions.put(id, s));

    }

    private @NonNull ImmutableSet<UUID> getEnabledExtensionIds() {
        return enabledExtensions.stream()
                                .map(extensionIds::get)
                                .filter(Objects::nonNull)
                                .collect(ImmutableSet.toImmutableSet());
    }

}
