package perobobbot.dvdlogo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandRegistry;
import perobobbot.dvdlogo.DVDLogoExtension;
import perobobbot.dvdlogo.DVDLogoExtensionFactory;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DVDLogoConfiguration {

    public static Packages provider() {
        return Packages.with(DVDLogoExtension.NAME,DVDLogoConfiguration.class);
    }

    private final @NonNull Overlay overlay;

    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull PolicyManager policyManager;

    @Bean
    public DVDLogoExtensionFactory dvdLogoExtensionFactirt() {
        return new DVDLogoExtensionFactory(overlay,policyManager,commandRegistry);
    }

}
