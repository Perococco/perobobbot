package perobobbot.puckwar.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Packages;
import perobobbot.overlay.api.Overlay;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.PuckWarExtensionFactory;

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
