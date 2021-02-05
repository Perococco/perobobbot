package perobobbot.connect4.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.connect4.Connect4Extension;
import perobobbot.connect4.game.PlayerFactoryParser;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MessageDispatcher;

@RequiredArgsConstructor
public class LaunchGame implements CommandAction {

    public static final String DEFAULT_AI = "a4";
    public static final String DEFAULT_CHAT = "c10";

    public static final String PLAYER1 = "player1";
    public static final String PLAYER2 = "player2";

    private final @NonNull Connect4Extension extension;

    private final @NonNull MessageDispatcher messageDispatcher;

    private final boolean bigMode;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var factory = new PlayerFactoryParser(messageDispatcher);
        final var p1 = parsing.findParameter(PLAYER1).orElse(DEFAULT_AI);
        final var p2 = parsing.findParameter(PLAYER2).orElse(DEFAULT_CHAT);

        extension.start(factory.parse(p1), factory.parse(p2), bigMode);
    }

}
