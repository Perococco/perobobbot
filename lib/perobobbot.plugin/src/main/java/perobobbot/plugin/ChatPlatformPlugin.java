package perobobbot.plugin;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;

@ExtensionPoint(version = 1)
public interface ChatPlatformPlugin extends PerobobbotPlugin {

    @NonNull ChatPlatform getChatPlatform();

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
