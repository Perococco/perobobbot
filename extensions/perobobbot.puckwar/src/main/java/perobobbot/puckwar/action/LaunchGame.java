package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.game.GameOptions;

import java.time.Duration;

@RequiredArgsConstructor
public class LaunchGame implements CommandAction {

    private final @NonNull PuckWarExtension puckWarExtension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var gameOptions = parseGameOptions(parsing);
        puckWarExtension.startGame(gameOptions);
    }

    private @NonNull GameOptions parseGameOptions(@NonNull CommandParsing parsing) {
        final var duration = Duration.ofSeconds(parsing.findIntParameter("duration").orElse(180));
        final var puckSize = parsing.findIntParameter("puckSize").orElse(20);

        return new GameOptions(puckSize, duration);
    }

}
