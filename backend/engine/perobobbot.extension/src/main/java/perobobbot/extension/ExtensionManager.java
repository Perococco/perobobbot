package perobobbot.extension;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.extension._private.PeroExtensionManager;

public interface ExtensionManager {

    static @NonNull ExtensionManager create(@NonNull ImmutableMap<String,Extension> extensions) {
        return PeroExtensionManager.create(extensions);
    }


    /**
     * Enable the extension with the provided name
     * @param extensionName the name of the extension to enable
     */
    void enableExtension(@NonNull String extensionName);

    /**
     * Disable the extension with the provided name
     * @param extensionName the name of the extension to disable
     */
    void disableExtension(@NonNull String extensionName);

    /**
     * Enable all extensions
     */
    void enableAll();

    /**
     * Disable all extensions
     */
    void disableAll();

    /**
     * @return information about all extensions
     */
    @NonNull
    ImmutableSet<ExtensionInfo> getExtensionInfo();

}
