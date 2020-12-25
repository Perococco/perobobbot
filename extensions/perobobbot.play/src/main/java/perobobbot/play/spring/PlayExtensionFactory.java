package perobobbot.play.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandBundle;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Bot;
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
    protected PlayExtension createExtension(@NonNull Bot bot, @NonNull Parameters parameters) {
        return new PlayExtension(parameters.getOverlay(), parameters.getSoundResolver());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(PlayExtension extension, @NonNull Parameters parameters) {
        final var policy = parameters.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(30)));
        return Optional.of(CommandBundle.builder()
                                        .add("play", policy, new PlaySound(extension))
                                        .build());
    }
}
