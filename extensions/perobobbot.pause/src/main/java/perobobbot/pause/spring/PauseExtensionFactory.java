package perobobbot.pause.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.pause.PauseExtension;
import perobobbot.pause.action.LaunchPause;

import java.time.Duration;

public class PauseExtensionFactory extends ExtensionFactoryBase<PauseExtension> {

    public static final String NAME = "pause";

    public PauseExtensionFactory() {
        super(NAME);
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
                factory.create("pause [duration]",accessRule,new LaunchPause(parameters.getIo(), extension)),
                factory.create("pause stop",accessRule,extension::stopPause)
        );
    }

}
