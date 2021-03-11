package perobobbot.extension;

import jplugman.api.Plugin;
import lombok.NonNull;

public interface ExtensionFactory extends Plugin {

    @NonNull String getExtensionName();


}
