package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.CommandRegistry;
import perococco.extension.SimpleExtensionManager;

public interface ExtensionManager {

    static @NonNull ExtensionManager create(@NonNull CommandRegistry commandRegistry, @NonNull AvailableExtensions extensions) {
        return new SimpleExtensionManager(commandRegistry,extensions);
    }

    void enableExtensions(@NonNull ImmutableSet<String> enabledExtensions);
}
