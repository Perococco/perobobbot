package perobobbot.pause.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.pause.PauseExtension;
import perobobbot.pause.action.ExecuteCommand;

import java.time.Duration;

@Component
public class PauseExtensionFactory extends ExtensionFactoryBase<PauseExtension> {

    public static Packages provider() {
        return Packages.with("[Extension] Pause", PauseExtensionFactory.class);
    }

    public static final String NAME = "pause";

    public PauseExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @NonNull
    @Override
    protected PauseExtension createExtension(@NonNull Parameters parameters) {
        return new PauseExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PauseExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO);

        return ImmutableList.of(
                factory.create("pause {param}",accessRule,new ExecuteCommand(parameters.getIo(), extension))
        );
    }

}
