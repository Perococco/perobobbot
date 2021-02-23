package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.lang.ServiceLoaderHelper;

import java.util.Comparator;
import java.util.ServiceLoader;

/**
 * @author Perococco
 */
public interface Plugin {

    Comparator<Plugin> COMPARE_NAME = Comparator.comparing(Plugin::getName);

    /**
     * @return the name of the plugin
     */
    @NonNull String getName();

    static @NonNull PluginList loadAllPlugins() {
        return new PluginList(ServiceLoaderHelper.getServices(ServiceLoader.load(Plugin.class)));
    }

}
