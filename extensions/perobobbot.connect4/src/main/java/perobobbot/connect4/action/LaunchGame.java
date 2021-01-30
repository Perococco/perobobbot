package perobobbot.connect4.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandException;
import perobobbot.command.CommandParsing;
import perobobbot.connect4.Connect4Extension;
import perobobbot.connect4.game.AI;
import perobobbot.connect4.game.Player;
import perobobbot.connect4.game.PlayerFactoryParser;
import perobobbot.connect4.game.TwitchViewers;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.fp.Value2;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class LaunchGame implements CommandAction {

    public static final String DEFAULT_AI = "a4";
    public static final String DEFAULT_CHAT = "c30";

    public static final String PLAYER1 = "player1";
    public static final String PLAYER2 = "player2";

    private final @NonNull Connect4Extension extension;

    private final @NonNull MessageDispatcher messageDispatcher;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var factory = new PlayerFactoryParser(messageDispatcher);
        final var p1 = parsing.findParameter(PLAYER1).orElse(DEFAULT_AI);
        final var p2 = parsing.findParameter(PLAYER2).orElse(DEFAULT_CHAT);

        extension.start(factory.parse(p1),factory.parse(p2));
    }

}
