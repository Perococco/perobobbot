package perobobbot.puckwar.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.extension.ExtensionWithCommands;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;
import java.util.Optional;

import static perobobbot.lang.RoleCoolDown.applyTo;

@Component
public class PuckWarExtensionFactory extends ExtensionFactoryBase<PuckWarExtension> {

    public static Packages provider() {
        return Packages.with("[Extension] Puck War", PuckWarExtensionFactory.class);
    }

    public PuckWarExtensionFactory(@NonNull Parameters parameters) {
        super(PuckWarExtension.NAME, parameters);
    }

    @Override
    protected @NonNull PuckWarExtension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new PuckWarExtension(userId, parameters.getOverlay());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull PuckWarExtension extension, @NonNull Parameters parameters) {
        final Policy policy = parameters.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Policy throwPolicy = parameters.createPolicy(AccessRule.create(Role.ANY_USER,
                Duration.ofSeconds(0),
                applyTo(Role.THE_BOSS).aCDof(0),
                applyTo(Role.ANY_USER).aCDof(10)
        ));
        return Optional.of(CommandBundle.builder()
                                        .add("pw start", policy, new LaunchGame(extension))
                                        .add("pw stop", policy, extension::requestStop)
                                        .add("pw stop now", policy, extension::stopGame)
                                        .add("throw", throwPolicy, new ThrowPuck(extension))
                                        .build());

    }
}
