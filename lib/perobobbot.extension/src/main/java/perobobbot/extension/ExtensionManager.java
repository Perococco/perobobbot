package perobobbot.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ExtensionPluginData;
import perococco.extension.SimpleExtensionManager;

import java.util.Optional;

public interface ExtensionManager {

    static @NonNull ExtensionManager create(@NonNull CommandRegistry commandRegistry) {
        return new SimpleExtensionManager(commandRegistry);
    }

    /**
     * @param extensionPluginData the extension plugin to add
     * @return an option containing a subscription that can be used to remove the extension to this manager, an
     * empty optional if the provided an extension plugin with the same name exists already.
     */
    @NonNull Optional<Subscription> addExtension(@NonNull ExtensionPluginData extensionPluginData);

    void enableExtensions(@NonNull ImmutableSet<String> enabledExtensions);
}
