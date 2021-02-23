package perobobbot.echo;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.echo.action.EchoExecutor;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;

import java.time.Duration;

public class EchoExtensionFactory extends ExtensionFactoryBase<EchoExtension> {

    public EchoExtensionFactory() {
        super(EchoExtension.NAME);
    }


    @Override
    protected @NonNull EchoExtension createExtension(@NonNull Parameters parameters) {
        return new EchoExtension(parameters.getIo());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull EchoExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10));
        return ImmutableList.of(
                factory.create("echo {message}",accessRule,new EchoExecutor(extension))
        );
    }

}
