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
    void updateExtensionList(@NonNull ImmutableSet<String> foundExtensionNames);

    /**
     * @return a list of all available extensions
     */
    @NonNull ImmutableList<Extension> listAllExtensions();

    /**
     * @param botId the id of a bot
     * @return the list of enabled extension for the provided bot
     */
    @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId);

    boolean isExtensionEnabled(@NonNull UUID botId, @NonNull UUID extensionId);

}
