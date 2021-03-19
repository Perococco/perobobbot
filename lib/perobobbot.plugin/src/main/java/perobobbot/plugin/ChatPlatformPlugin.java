package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;

public interface ChatPlatformPlugin extends PerobobbotPlugin {

    @NonNull ChatPlatform getChatPlatform();

    @Override
    default public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
