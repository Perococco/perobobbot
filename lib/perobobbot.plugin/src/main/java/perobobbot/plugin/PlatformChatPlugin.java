package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;

public interface PlatformChatPlugin extends PluginUsingServices {

    /**
     * @param serviceProvider a provider of service
     * @return the chatplatform this plugin provides
     */
    @NonNull ChatPlatform create(@NonNull ServiceProvider serviceProvider);

}
