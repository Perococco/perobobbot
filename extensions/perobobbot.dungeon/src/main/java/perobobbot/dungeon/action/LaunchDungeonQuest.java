package perobobbot.dungeon.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.dungeon.DungeonExtension;
import perobobbot.lang.ExecutionContext;
import perococco.jdgen.api.JDGenConfiguration;

import java.util.Random;

@RequiredArgsConstructor
public class LaunchDungeonQuest implements CommandAction {

    private final @NonNull DungeonExtension extension;

    private final Random random = new Random();

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var size = parsing.findIntParameter("size").orElse(10);
        final var minRoomSize = 2;
        final var maxRoomSize = 6;
        final var configuration = new JDGenConfiguration(random.nextLong(),size,minRoomSize,maxRoomSize,1.25);
        extension.start(configuration);
    }
}
