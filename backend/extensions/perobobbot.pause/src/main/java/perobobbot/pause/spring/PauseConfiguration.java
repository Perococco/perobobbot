package perobobbot.pause.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Packages;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.PauseExtensionFactory;

@Configuration
@RequiredArgsConstructor
public class PauseConfiguration {

    public static Packages provider() {
        return Packages.with("pause",PauseConfiguration.class);
    }

    private final @NonNull Overlay overlay;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull PolicyManager policyManager;


    @Bean
    public @NonNull PauseExtensionFactory pauseExtensionFactory() {
        return new PauseExtensionFactory(overlay,commandRegistry,policyManager);
    }

}
