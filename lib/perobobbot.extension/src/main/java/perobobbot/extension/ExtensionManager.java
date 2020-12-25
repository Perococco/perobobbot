package perobobbot.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.ROCommandRegistry;
import perobobbot.lang.Bot;

public interface ExtensionManager extends ROCommandRegistry {


    /**
     * @return the bot this extension manager is associated to.
     */
    @NonNull Bot getBot();

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
