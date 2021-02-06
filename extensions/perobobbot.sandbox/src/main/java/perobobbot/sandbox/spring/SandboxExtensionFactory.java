package perobobbot.sandbox.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;
import perobobbot.sandbox.SandboxExtension;

import java.time.Duration;

public class SandboxExtensionFactory extends ExtensionFactoryBase<SandboxExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION, SandboxExtension.NAME, SandboxExtensionFactory.class);
    }

    public SandboxExtensionFactory(@NonNull Parameters parameters) {
        super(SandboxExtension.NAME, parameters);
    }

    @Override
    protected @NonNull SandboxExtension createExtension(@NonNull Parameters parameters) {
        return new SandboxExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull SandboxExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));

        return ImmutableList.of(
                factory.create("sb",accessRule,extension::start),
                factory.create("sb stop",accessRule,extension::stop)
        );
    }
}
