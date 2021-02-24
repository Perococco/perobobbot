package perobobbot.chatpoll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.chat.core.IO;
import perobobbot.chatpoll.action.PollLauncher;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.messaging.ChatController;
import perobobbot.overlay.api.Overlay;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

public class ChatPollExtensionFactory extends ExtensionFactoryBase<ChatPollExtension> {


    public ChatPollExtensionFactory() {
        super(ChatPollExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(ChatController.class),
                               Requirement.required(Overlay.class),
                               Requirement.required(IO.class));
    }

    @Override
    protected @NonNull ChatPollExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new ChatPollExtension(
                serviceProvider.getService(ChatController.class),
                serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(
            @NonNull ChatPollExtension extension,
            @NonNull ServiceProvider serviceProvider,
            @NonNull CommandDefinition.Factory factory) {
        final var accessRule = AccessRule.create(Role.TRUSTED_USER, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("poll", accessRule, new PollLauncher(extension,serviceProvider.getService(IO.class))),
                factory.create("poll stop", accessRule, extension::stop)
        );
    }

}
