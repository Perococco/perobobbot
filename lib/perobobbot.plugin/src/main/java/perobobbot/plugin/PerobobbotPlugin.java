package perobobbot.plugin;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

/**
 * @author perococco
 */
@ExtensionPoint(version = 1)
public interface PerobobbotPlugin {

    /**
     * @return the name of the plugin
     */
    @NonNull String getName();

    /**
     * @return the data defining this plugin
     */
    @NonNull PerobobbotPluginData getData();
}
