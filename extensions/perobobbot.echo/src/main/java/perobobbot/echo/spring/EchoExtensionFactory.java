package perobobbot.echo.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.echo.EchoExtension;
import perobobbot.echo.action.EchoExecutor;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

@Component
public class EchoExtensionFactory extends ExtensionFactoryBase<EchoExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"Echo", EchoExtensionFactory.class);
    }

    public EchoExtensionFactory(@NonNull Parameters parameters) {
        super(EchoExtension.NAME, parameters);
    }

    @Override
    protected @NonNull EchoExtension createExtension(@NonNull Parameters parameters) {
        return new EchoExtension(parameters.getIo());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull EchoExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10));
        return ImmutableList.of(
                factory.create("echo",accessRule,new EchoExecutor(extension))
        );
    }

}
