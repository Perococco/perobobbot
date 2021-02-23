package perobobbot.localio.spring;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.localio.LocalChatPlatform;
import perobobbot.plugin.PlatformChatPlugin;

public class LocalIOChatPlugin implements PlatformChatPlugin {

    @Override
    public @NonNull String getName() {
        return "Local IO";
    }

    @Override
    public @NonNull ChatPlatform create(@NonNull Parameters parameters) {
        return new LocalChatPlatform(parameters.getApplicationCloser(),
                                     parameters.getBotService(),
                                     parameters.getStandardInputProvider(),
                                     parameters.getInstants());
    }
}
