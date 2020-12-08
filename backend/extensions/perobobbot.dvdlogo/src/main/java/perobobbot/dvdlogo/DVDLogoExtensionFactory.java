package perobobbot.dvdlogo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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

import java.time.Duration;

@RequiredArgsConstructor
public class DVDLogoExtensionFactory implements ExtensionFactory {

    private final @NonNull Overlay overlay;

    private final @NonNull PolicyManager policyManager;

    private final @NonNull CommandRegistry commandRegistry;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        return new DVDLogoExtension(userId,overlay,this::createCommandBundleLifeCycle);
    }

    private @NonNull CommandBundleLifeCycle createCommandBundleLifeCycle(@NonNull DVDLogoExtension extension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));

        return CommandBundle.builder()
                            .add("dl start", policy, extension::startOverlay)
                            .add("dl stop", policy, extension::stopOverlay)
                            .build()
                            .createLifeCycle(commandRegistry);

    }

    @Override
    public @NonNull String getExtensionName() {
        return DVDLogoExtension.NAME;
    }
}
