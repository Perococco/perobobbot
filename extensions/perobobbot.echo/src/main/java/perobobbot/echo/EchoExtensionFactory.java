package perobobbot.echo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandDefinition;
import perobobbot.echo.action.EchoExecutor;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

public class EchoExtensionFactory extends ExtensionFactoryBase<EchoExtension> {

    public EchoExtensionFactory() {
        super(EchoExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(IO.class));
    }

    @Override
    protected @NonNull EchoExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new EchoExtension(serviceProvider.getService(IO.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull EchoExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10));
        return ImmutableList.of(
                factory.create("echo {message}",accessRule,new EchoExecutor(extension))
        );
    }

}
