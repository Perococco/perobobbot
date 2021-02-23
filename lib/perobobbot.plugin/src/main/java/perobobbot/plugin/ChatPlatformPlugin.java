package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.Instants;

public interface ChatPlatformPlugin extends Plugin {

    @NonNull ChatPlatform create(@NonNull Instants instants);

}
