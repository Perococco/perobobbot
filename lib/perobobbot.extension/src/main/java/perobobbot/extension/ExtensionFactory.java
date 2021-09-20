package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import jplugman.api.ServiceProvider;
import lombok.NonNull;
import perobobbot.command.CommandDeclaration;
import perobobbot.plugin.Extension;

public interface ExtensionFactory<E extends Extension> {

    /**
     * @param  pluginLayer the module layer used to load the plugin
     * @param serviceProvider the service provider containing the services the extension required
     * @return a new instance of the extension
     */
    @NonNull E createExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);

    /**
     * @param extension  the extension the commands will apply to
     * @param serviceProvider the service provider containing the services the extension required
     * @param factory this factory parameters containing some services the extension might use
     * @return an optional containing the command bundle that command the extension, an empty optional if the extension has no command
     */
    @NonNull ImmutableList<CommandDeclaration> createCommandDefinitions(@NonNull E extension, @NonNull ServiceProvider serviceProvider, @NonNull CommandDeclaration.Factory factory);


}
