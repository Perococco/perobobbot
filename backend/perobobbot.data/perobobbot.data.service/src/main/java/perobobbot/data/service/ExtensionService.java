package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.data.com.Extension;

import java.util.UUID;

public interface ExtensionService {

    /**
     * Update the available flag accordingly to the set of extensions found at runtime
     * @param foundExtensionNames the set of extension name found at runtime
     */
    @NonNull void updateExtensionList(@NonNull ImmutableSet<String> foundExtensionNames);

    /**
     * @return a list of all available extensions
     */
    @NonNull ImmutableList<Extension> listAllExtensions();

    /**
     * @return a list of all available and enabled extensions
     */
    @NonNull ImmutableSet<Extension> listEnabledExtensions();

    /**
     * @param botId the id of a bot
     * @return the list of enabled extension for the provided bot
     */
    @NonNull ImmutableSet<Extension> listEnabledExtensions(@NonNull UUID botId);

}
