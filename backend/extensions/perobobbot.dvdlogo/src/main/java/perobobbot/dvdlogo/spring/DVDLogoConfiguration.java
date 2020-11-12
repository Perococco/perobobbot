package perobobbot.dvdlogo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.common.command.Command;
import perobobbot.common.command.CommandBundle;
import perobobbot.dvdlogo.DVDLogoExtension;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DVDLogoConfiguration {

    public static Packages provider() {
        return Packages.with(DVDLogoExtension.EXTENSION_NAME,DVDLogoConfiguration.class);
    }

    private final @NonNull Overlay overlay;

    private final @NonNull PolicyManager policyManager;

    @Bean
    public DVDLogoExtension dvdLogoExtension() {
        return new DVDLogoExtension(overlay);
    }

    @Bean(name = DVDLogoExtension.EXTENSION_NAME)
    public CommandBundle commandBundle(@NonNull DVDLogoExtension dvdLogoExtension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));

        return Command.factory()
                .add("dl start", policy, dvdLogoExtension::startOverlay)
                .add("dl stop", policy, dvdLogoExtension::stopOverlay)
                .build();
    }
}
