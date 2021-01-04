package perobobbot.dvdlogo.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.dvdlogo.DVDLogoExtension;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

@Component
public class DVDLogoExtensionFactory extends ExtensionFactoryBase<DVDLogoExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"DVD Logo", DVDLogoExtensionFactory.class);
    }

    public DVDLogoExtensionFactory( @NonNull Parameters parameters) {
        super(DVDLogoExtension.NAME, parameters);
    }

    @Override
    protected @NonNull DVDLogoExtension createExtension(@NonNull Parameters parameters) {
        return new DVDLogoExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull DVDLogoExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("dl start",accessRule,extension::startOverlay),
                factory.create("dl stop",accessRule,extension::stopOverlay)
        );
    }

}
