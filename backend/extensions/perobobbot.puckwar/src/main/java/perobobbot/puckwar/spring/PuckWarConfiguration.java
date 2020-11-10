package perobobbot.puckwar.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.common.command.Command;
import perobobbot.common.command.CommandBundle;
import perobobbot.common.lang.Packages;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.RoleCooldown;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.LaunchGame;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.SetNiceState;
import perobobbot.puckwar.ThrowPuck;

import java.time.Duration;

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
        final Policy throwPolicy = policyManager.createPolicy(AccessRule.create(Role.STANDARD_USER, Duration.ofSeconds(10), new RoleCooldown(Role.THE_BOSS,Duration.ZERO)));
        return Command.factory()
                      .add("pw start", policy, new LaunchGame(puckWarExtension))
                      .add("pw nice", policy, new SetNiceState(puckWarExtension))
                      .add("pw stop", policy, puckWarExtension::stopGame)
                      .add("pw throw", throwPolicy, new ThrowPuck(puckWarExtension))
                      .build();
    }
}
