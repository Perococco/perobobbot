package perobobbot.plugin;

import lombok.NonNull;

/**
 * @author perococco
 */
public interface PerobobbotPlugin {

    /**
     * @return the name of the plugin
     */
    @NonNull String getName();

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {
        @NonNull T visit(@NonNull ExtensionPlugin extensionPlugin);
        @NonNull T visit(@NonNull ChatPlatformPlugin chatPlatformPlugin);
        @NonNull T visit(@NonNull WebPlugin webPlugin);
    }
}
