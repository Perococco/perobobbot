package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.ROCommandRegistry;
import perobobbot.lang.Bot;

@RequiredArgsConstructor
public class AvailableExtensions {

    /**
     * All the extension factories found by Spring
     */
    private final @NonNull ImmutableMap<String, ExtensionInfo> extensionsByName;

    public @NonNull ImmutableSet<String> getNameOfExtensions() {
        return extensionsByName.keySet();
    }

}
