package perobobbot.dvdlogo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

public class DVDLogoExtensionFactory extends ExtensionFactoryBase<DVDLogoExtension> {

    public DVDLogoExtensionFactory() {
        super(DVDLogoExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class));
    }

    @Override
    protected @NonNull DVDLogoExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new DVDLogoExtension(serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull DVDLogoExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("dl",accessRule,extension::startOverlay),
                factory.create("dl stop",accessRule,extension::stopOverlay)
        );
    }

}
