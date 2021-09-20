package perobobbot.plugin;

import lombok.NonNull;

/**
 * @author perococco
 */
public sealed interface PerobobbotPluginData
permits WebPluginData, ChatPlatformPluginData, ExtensionPluginData, EndPointPluginData
{

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {
        @NonNull T visit(@NonNull ExtensionPluginData extensionPlugin);
        @NonNull T visit(@NonNull ChatPlatformPluginData chatPlatformPluginData);
        @NonNull T visit(@NonNull WebPluginData webPluginData);
        @NonNull T visit(@NonNull EndPointPluginData endPointPluginData);
    }
}
