package perobobbot.plugin;

import lombok.NonNull;
import lombok.Value;
import perobobbot.chat.core.ChatPlatform;

/**
 * A plugin to allow the communication on a platform chat
 */
public record ChatPlatformPluginData(
        @NonNull ChatPlatform chatPlatform) implements PerobobbotPluginData {

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
