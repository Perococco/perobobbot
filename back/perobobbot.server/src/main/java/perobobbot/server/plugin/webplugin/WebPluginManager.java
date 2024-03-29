package perobobbot.server.plugin.webplugin;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perobobbot.plugin.WebPluginData;

public interface WebPluginManager {

    /**
     * @param webPluginData the web plugin to add
     * @return a subscription that can be used to remove the provided web plugin
     */
    @NonNull Subscription addWebPlugin(@NonNull WebPluginData webPluginData);

}
