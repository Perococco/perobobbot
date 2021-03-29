package perobobbot.extension;

import jplugman.api.Plugin;
import lombok.NonNull;
import perobobbot.plugin.ExtensionPlugin;

public interface ExtensionFactory extends Plugin {

    @NonNull String getExtensionName();


}
