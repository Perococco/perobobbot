package perobobbot.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface ExtensionManager {


    /**
     * @return id of the user this extension manager is associated to.
     */
    @NonNull String getUserId();

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
