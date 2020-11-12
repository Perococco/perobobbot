package perobobbot.extension._private;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionInfo;
import perobobbot.extension.ExtensionManager;
import perobobbot.extension.UnknownExtension;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PeroExtensionManager implements ExtensionManager {

    public static @NonNull ExtensionManager create(@NonNull ImmutableMap<String, Extension> extensions) {
        final ExtensionManager extensionManager = new PeroExtensionManager(extensions);

        extensions.values().stream()
                  .filter(Extension::isAutoStart)
                  .map(Extension::getName)
                  .forEach(extensionManager::enableExtension);

        return extensionManager;
    }

    private final @NonNull ImmutableMap<String, Extension> extensions;

    private @NonNull Extension getExtension(@NonNull String extensionName) {
        final var extension = extensions.get(extensionName);
        if (extension == null) {
            throw new UnknownExtension(extensionName);
        }
        return extension;
    }

    @Override
    public void enableExtension(@NonNull String extensionName) {
        getExtension(extensionName).enable();
    }

    @Override
    public void disableExtension(@NonNull String extensionName) {
        getExtension(extensionName).disable();
    }

    @Override
    public void enableAll() {
        extensions.values().forEach(Extension::enable);
    }

    @Override
    public void disableAll() {
        extensions.values().forEach(Extension::disable);
    }

    @Override
    public @NonNull ImmutableSet<ExtensionInfo> getExtensionInfo() {
        return extensions.values().stream()
                         .map(e -> new ExtensionInfo(e.getName(), e.isEnabled()))
                         .collect(ImmutableSet.toImmutableSet());
    }
}
