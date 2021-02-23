package perobobbot.dvdlogo;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;

import java.time.Duration;

public class DVDLogoExtensionFactory extends ExtensionFactoryBase<DVDLogoExtension> {

    public DVDLogoExtensionFactory() {
        super(DVDLogoExtension.NAME);
    }

    @Override
    protected @NonNull DVDLogoExtension createExtension(@NonNull Parameters parameters) {
        return new DVDLogoExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull DVDLogoExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("dl",accessRule,extension::startOverlay),
                factory.create("dl stop",accessRule,extension::stopOverlay)
        );
    }

}
