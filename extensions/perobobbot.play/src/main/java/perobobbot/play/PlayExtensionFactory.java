package perobobbot.play;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.play.PlayExtension;
import perobobbot.play.action.ListSound;
import perobobbot.play.action.PlaySound;

import java.time.Duration;

public class PlayExtensionFactory extends ExtensionFactoryBase<PlayExtension> {

    public PlayExtensionFactory() {
        super(PlayExtension.NAME);
    }

    @Override
    protected PlayExtension createExtension(@NonNull Parameters parameters) {
        return new PlayExtension(parameters.getOverlay(), parameters.getChatController(), parameters.getSoundResolver());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PlayExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30), Role.THE_BOSS.cooldown(Duration.ZERO));
        return ImmutableList.of(
                factory.create("play {soundName}", accessRule, new PlaySound(extension)),
                factory.create("play?", accessRule, new ListSound(extension, parameters.getIo()))
        );
    }
}
