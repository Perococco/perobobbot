package perobobbot.twitch.chat.spring;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.plugin.PlatformChatPlugin;
import perobobbot.twitch.chat.TwitchChatPlatform;

public class TwitchChatPlugin implements PlatformChatPlugin {


    @Override
    public @NonNull ChatPlatform create(@NonNull Parameters parameters) {
        return new TwitchChatPlatform(parameters.getInstants());
    }

    @Override
    public @NonNull String getName() {
        return "Twitch Chat Platform";
    }

}
