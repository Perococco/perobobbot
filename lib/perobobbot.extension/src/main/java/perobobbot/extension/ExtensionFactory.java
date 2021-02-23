package perobobbot.extension;

import lombok.NonNull;
import perobobbot.plugin.ExtensionInfo;

public interface ExtensionFactory {

    @NonNull String getExtensionName();

    @NonNull ExtensionInfo create();


}
