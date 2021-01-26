package perobobbot.chatpoll.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.chatpoll.ChatPollExtension;
import perobobbot.chatpoll.action.PollLauncher;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

@Component
public class ChatPollExtensionFactory extends ExtensionFactoryBase<ChatPollExtension> {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.EXTENSION, "Chat Poll", ChatPollExtensionFactory.class);
    }

    public ChatPollExtensionFactory(@NonNull Parameters parameters) {
        super(ChatPollExtension.NAME, parameters);
    }

    @Override
    protected @NonNull ChatPollExtension createExtension(@NonNull Parameters parameters) {
        return new ChatPollExtension(parameters.getChatController(),parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(
            @NonNull ChatPollExtension extension,
            @NonNull Parameters parameters,
            @NonNull CommandDefinition.Factory factory) {
        final var accessRule = AccessRule.create(Role.TRUSTED_USER, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("poll start", accessRule, new PollLauncher(extension,parameters.getIo())),
                factory.create("poll stop", accessRule, extension::stop)
        );
    }

}
