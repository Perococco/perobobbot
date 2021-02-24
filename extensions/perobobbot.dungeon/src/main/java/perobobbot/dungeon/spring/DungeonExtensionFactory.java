package perobobbot.dungeon.spring;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.command.CommandDefinition;
import perobobbot.dungeon.DungeonExtension;
import perobobbot.dungeon.action.DebugDungeon;
import perobobbot.dungeon.action.LaunchDungeonQuest;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

public class DungeonExtensionFactory extends ExtensionFactoryBase<DungeonExtension> {

    public DungeonExtensionFactory() {
        super(DungeonExtension.NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class));
    }

    @Override
    protected @NonNull DungeonExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new DungeonExtension(serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(@NonNull DungeonExtension extension, @NonNull ServiceProvider serviceProvider, CommandDefinition.@NonNull Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO);
        return ImmutableList.of(
                factory.create("dg [size]",accessRule, new LaunchDungeonQuest(extension)),
                factory.create("dg stop",accessRule, extension::stop),
                factory.create("dg debug [size] [seed]",accessRule, new DebugDungeon(extension))
        );
    }
}
