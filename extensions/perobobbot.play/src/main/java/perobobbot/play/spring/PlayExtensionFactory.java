package perobobbot.play.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.play.PlayExtension;
import perobobbot.play.action.PlaySound;

import java.time.Duration;
import java.util.Optional;

@Component
public class PlayExtensionFactory extends ExtensionFactoryBase<PlayExtension> {

    public static @NonNull Packages provider() {
        return Packages.with("[Extension] Play", PlayExtensionFactory.class);
    }

    public PlayExtensionFactory(@NonNull Parameters parameters) {
        super(PlayExtension.NAME, parameters);
    }

    @Override
    protected PlayExtension createExtension(@NonNull Parameters parameters) {
        return new PlayExtension(parameters.getOverlay(), parameters.getSoundResolver());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PlayExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30));
        return ImmutableList.of(
                factory.create("play {soundName}", accessRule, new PlaySound(extension))
        );
    }
}
