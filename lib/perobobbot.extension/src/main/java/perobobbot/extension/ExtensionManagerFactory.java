package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;

@RequiredArgsConstructor
public class ExtensionManagerFactory implements ExtensionManager {

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
    private final @NonNull ImmutableMap<String, ExtensionInfo> extensions;

    @Synchronized
    public void disableAll() {
        extensions.forEach((e,v) -> v.disable());
    }

    @Override
    public @NonNull ImmutableSet<String> listAllExtensions() {
        return extensions.keySet();
    }
}
