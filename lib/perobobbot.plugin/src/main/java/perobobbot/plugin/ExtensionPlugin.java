package perobobbot.plugin;

import lombok.NonNull;

public interface ExtensionPlugin extends PluginUsingServices {

    /**
     * @param serviceProvider a provider of service
     * @return the extension and some information about it
     */
    @NonNull ExtensionInfo create(@NonNull ServiceProvider serviceProvider);

}
