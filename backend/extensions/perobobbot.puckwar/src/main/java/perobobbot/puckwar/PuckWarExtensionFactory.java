package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotification;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;

import static perobobbot.lang.RoleCooldown.applyTo;

@RequiredArgsConstructor
public class PuckWarExtensionFactory implements ExtensionFactory {


    private final @NonNull Overlay overlay;

    private final @NonNull PolicyManager policyManager;

    private final @NonNull CommandRegistry commandRegistry;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        return new PuckWarExtension(overlay, this::createCommandBundleLifeCycle);
    }

    private CommandBundleLifeCycle createCommandBundleLifeCycle(@NonNull PuckWarExtension puckWarExtension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Policy throwPolicy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER,
                Duration.ofSeconds(0),
                applyTo(Role.THE_BOSS).aCDof(0),
                applyTo(Role.ANY_USER).aCDof(10)
        ));
        return CommandBundle.builder()
                            .add("pw start", policy, new LaunchGame(puckWarExtension))
                            .add("pw stop", policy, puckWarExtension::requestStop)
                            .add("pw stop now", policy, puckWarExtension::stopGame)
                            .add("throw", throwPolicy, new ThrowPuck(puckWarExtension))
                            .build()
                            .createLifeCycle(commandRegistry);

    }

    @Override
    public @NonNull String getExtensionName() {
        return PuckWarExtension.NAME;
    }
}
