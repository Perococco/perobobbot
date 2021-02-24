package perobobbot.puckwar;

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
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;

import static perobobbot.lang.RoleCoolDown.applyTo;

public class PuckWarExtensionFactory extends ExtensionFactoryBase<PuckWarExtension> {

    public PuckWarExtensionFactory() {
        super(PuckWarExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class));
    }

    @Override
    protected @NonNull PuckWarExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new PuckWarExtension(serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PuckWarExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final AccessRule accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        final AccessRule throwAccessRule = AccessRule.create(Role.ANY_USER, Duration.ofSeconds(0),
                applyTo(Role.THE_BOSS).aCDof(0),
                applyTo(Role.ANY_USER).aCDof(10));

        return ImmutableList.of(
            factory.create("pw [duration] [puckSize]",accessRule, new LaunchGame(extension)),
            factory.create("pw stop",accessRule,extension::requestStop),
            factory.create("pw stop now",accessRule,extension::stopGame),
            factory.create("throw {speed} {angle}",throwAccessRule,new ThrowPuck(extension))
        );
    }

}
