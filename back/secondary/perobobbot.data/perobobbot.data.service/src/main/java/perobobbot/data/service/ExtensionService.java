package perobobbot.data.service;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.Extension;

import java.util.UUID;

public interface ExtensionService {

    /**
     * Set the extension with the provided name as available
     * @param extensionName the name of the extension to set as available
     */
    void setExtensionAvailable(@NonNull String extensionName);

    /**
     * Set the extension with the provided name as unavailable
     * @param extensionName the name of the extension to set as unavailable
     */
    void setExtensionUnavailable(@NonNull String extensionName);

    /**
     * @return a list of all available extensions
     */
    @NonNull ImmutableList<Extension> listAllExtensions();

    /**
     * @param extensionName the name of the extension to activate
     * @return true if the extension has been modified (i.e. was not activated), false otherwise
     */
    boolean activateExtension(@NonNull String extensionName);

    /**
     * @param extensionName the name of the extension to deactivate
     * @return true if the extension has been modified (i.e. was activated), false otherwise
     */
    boolean deactivateExtension(@NonNull String extensionName);

    /**
     * @param botId the id of a bot
     * @return the list of enabled extension for the provided bot
     */
    @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId);

    boolean isExtensionEnabled(@NonNull UUID botId, @NonNull String extensionName);


}
