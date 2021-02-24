package perobobbot.twitch.chat.spring;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.Instants;
import perobobbot.plugin.PlatformChatPlugin;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;
import perobobbot.twitch.chat.TwitchChatPlatform;

public class TwitchChatPlugin implements PlatformChatPlugin {

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Instants.class));
    }

    @Override
    public @NonNull ChatPlatform create(@NonNull ServiceProvider serviceProvider) {
        return new TwitchChatPlatform(serviceProvider.getService(Instants.class));
    }

    @Override
    public @NonNull String getName() {
        return "Twitch Chat Platform";
    }

}
