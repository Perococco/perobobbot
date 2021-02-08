package perobobbot.dungeon.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.dungeon.DungeonExtension;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.dungeon.action.LaunchDungeonQuest;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

@Component
public class DungeonExtensionFactory extends ExtensionFactoryBase<DungeonExtension> {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"Dungeon Game",DungeonExtensionFactory.class);
    }

    public DungeonExtensionFactory(@NonNull Parameters parameters) {
        super(DungeonExtension.NAME, parameters);
    }

    @Override
    protected @NonNull DungeonExtension createExtension(@NonNull Parameters parameters) {
        return new DungeonExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull DungeonExtension extension, @NonNull Parameters parameters, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO);
        return ImmutableList.of(
                factory.create("dg [size]",accessRule, new LaunchDungeonQuest(extension)),
                factory.create("dg stop",accessRule, extension::stop)
        );
    }
}
