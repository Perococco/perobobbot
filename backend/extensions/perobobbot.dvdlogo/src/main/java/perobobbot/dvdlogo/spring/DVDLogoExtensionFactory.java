package perobobbot.dvdlogo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.dvdlogo.DVDLogoExtension;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.extension.ExtensionWithCommands;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;
import java.util.Optional;

@Component
public class DVDLogoExtensionFactory extends ExtensionFactoryBase<DVDLogoExtension> {


    public static Packages provider() {
        return Packages.with("[Extension] DVD Logo",DVDLogoExtensionFactory.class);
    }

    public DVDLogoExtensionFactory( @NonNull Parameters parameters) {
        super(DVDLogoExtension.NAME, parameters);
    }

    @Override
    protected @NonNull DVDLogoExtension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new DVDLogoExtension(userId,parameters.getOverlay());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull DVDLogoExtension extension, @NonNull Parameters parameters) {
        final Policy policy = parameters.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));

        return Optional.of(CommandBundle.builder()
                                        .add("dl start", policy, extension::startOverlay)
                                        .add("dl stop", policy, extension::stopOverlay)
                                        .build());

    }

}
