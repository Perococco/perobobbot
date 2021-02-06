package perobobbot.puckwar.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.action.LaunchGame;
import perobobbot.puckwar.action.ThrowPuck;

import java.time.Duration;

import static perobobbot.lang.RoleCoolDown.applyTo;

@Component
public class PuckWarExtensionFactory extends ExtensionFactoryBase<PuckWarExtension> {

    public static Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"Puck War", PuckWarExtensionFactory.class);
    }

    public PuckWarExtensionFactory(@NonNull Parameters parameters) {
        super(PuckWarExtension.NAME, parameters);
    }

    @Override
    protected @NonNull PuckWarExtension createExtension(@NonNull Parameters parameters) {
        return new PuckWarExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull PuckWarExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
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
