package perococco.extension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.AvailableExtensions;
import perobobbot.extension.ExtensionInfo;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.Subscription;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleExtensionManager implements ExtensionManager {

    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull AvailableExtensions availableExtensions;

    private final Map<String, Subscription> subscriptions = new HashMap<>();

    @Synchronized
    public void enableExtensions(@NonNull ImmutableSet<String> enabledExtensions) {
        final var currentlyEnabled = ImmutableSet.copyOf(subscriptions.keySet());
        final var toDisable = Sets.difference(currentlyEnabled, enabledExtensions);
        final var toEnable = Sets.difference(enabledExtensions,currentlyEnabled);

        for (String name : toDisable) {
            this.subscriptions.remove(name).unsubscribe();
        }

        for (String name : toEnable) {
            availableExtensions.find(name)
                               .ifPresent(e -> {
                                   this.subscriptions.put(e.getExtensionName(),commandRegistry.addCommandDefinitions(e.getCommandDefinitions()));
                               });
        }
    }
}
