package perobobbot.play;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.play.PlayExtension;
import perobobbot.play.action.ListSound;
import perobobbot.play.action.PlaySound;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;
import perobobbot.sound.SoundResolver;

import java.time.Duration;

public class PlayExtensionFactory extends ExtensionFactoryBase<PlayExtension> {

    public PlayExtensionFactory() {
        super(PlayExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class),
                               Requirement.required(MessageDispatcher.class),
                               Requirement.required(SoundResolver.class),
                               Requirement.required(IO.class));
    }

    @Override
    protected PlayExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new PlayExtension(
                serviceProvider.getService(Overlay.class),
                serviceProvider.getService(MessageDispatcher.class),
                serviceProvider.getService(SoundResolver.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PlayExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30),
                                                 Role.THE_BOSS.cooldown(Duration.ZERO));
        return ImmutableList.of(
                factory.create("play {soundName}", accessRule, new PlaySound(extension)),
                factory.create("play?", accessRule, new ListSound(extension, serviceProvider.getService(IO.class)))
        );
    }
}
