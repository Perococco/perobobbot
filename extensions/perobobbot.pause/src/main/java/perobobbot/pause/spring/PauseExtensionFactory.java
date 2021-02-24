package perobobbot.pause.spring;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.PauseExtension;
import perobobbot.pause.action.LaunchPause;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

public class PauseExtensionFactory extends ExtensionFactoryBase<PauseExtension> {

    public static final String NAME = "pause";

    public PauseExtensionFactory() {
        super(NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class), Requirement.required(IO.class));
    }

    @NonNull
    @Override
    protected PauseExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new PauseExtension(serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PauseExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO);

        return ImmutableList.of(
                factory.create("pause [duration]",accessRule,new LaunchPause(serviceProvider.getService(IO.class), extension)),
                factory.create("pause stop",accessRule,extension::stopPause)
        );
    }

}
