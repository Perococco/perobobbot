package perobobbot.puckwar.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;

import static perobobbot.lang.RoleCooldown.applyTo;

@Configuration
@RequiredArgsConstructor
public class PuckWarConfiguration {

    public static Packages provider() {
        return Packages.with(PuckWarExtension.EXTENSION_NAME, PuckWarConfiguration.class);
    }

    private final @NonNull Overlay overlay;
    private final @NonNull PolicyManager policyManager;

    @Bean
    public PuckWarExtension puckWarExtension() {
        return new PuckWarExtension(overlay);
    }

    @Bean(name = PuckWarExtension.EXTENSION_NAME)
    public CommandBundle commandBundle(@NonNull PuckWarExtension puckWarExtension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Policy throwPolicy = policyManager.createPolicy(AccessRule.create(Role.STANDARD_USER,
                                                                                Duration.ofSeconds(0),
                                                                                applyTo(Role.THE_BOSS).aCDof(0),
                                                                                applyTo(Role.ANY_USER).aCDof(10)
        ));
        return CommandBundle.builder()
                      .add("pw start", policy, new LaunchGame(puckWarExtension))
                      .add("pw stop", policy, puckWarExtension::stopGame)
                      .add("throw", throwPolicy, new ThrowPuck(puckWarExtension))
                      .build();
    }
}
