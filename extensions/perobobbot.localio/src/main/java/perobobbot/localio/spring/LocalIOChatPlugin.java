package perobobbot.localio.spring;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.data.service.BotService;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Instants;
import perobobbot.lang.StandardInputProvider;
import perobobbot.localio.LocalChatPlatform;
import perobobbot.plugin.PlatformChatPlugin;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

public class LocalIOChatPlugin implements PlatformChatPlugin {

    @Override
    public @NonNull String getName() {
        return "Local IO";
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(
                Requirement.required(ApplicationCloser.class),
                Requirement.required(BotService.class),
                Requirement.required(StandardInputProvider.class),
                Requirement.required(Instants.class)
        );
    }

    @Override
    public @NonNull ChatPlatform create(@NonNull ServiceProvider serviceProvider) {
        return new LocalChatPlatform(
                serviceProvider.getService(ApplicationCloser.class),
                serviceProvider.getService(BotService.class),
                serviceProvider.getService(StandardInputProvider.class),
                serviceProvider.getService(Instants.class)
        );
    }
}
