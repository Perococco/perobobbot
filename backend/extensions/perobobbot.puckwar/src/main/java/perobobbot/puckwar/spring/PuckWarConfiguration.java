package perobobbot.puckwar.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.PuckWarExtensionFactory;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;

import static perobobbot.lang.RoleCooldown.applyTo;

@Configuration
@RequiredArgsConstructor
public class PuckWarConfiguration {

    public static Packages provider() {
        return Packages.with(PuckWarExtension.NAME, PuckWarConfiguration.class);
    }

    private final @NonNull Overlay overlay;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull CommandRegistry commandRegistry;

    @Bean
    public PuckWarExtensionFactory puckWarExtensionFactory() {
        return new PuckWarExtensionFactory(overlay,policyManager,commandRegistry);
    }

}
