package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandException;
import perobobbot.lang.MessageDispatcher;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PlayerFactoryParser {

    private static final Pattern ENCODED_FACTORY = Pattern.compile("([ac])(\\d+)");

    private final @NonNull MessageDispatcher messageDispatcher;

    public @NonNull Player.Factory parse(@NonNull String encodedFactory) {
        final var match = ENCODED_FACTORY.matcher(encodedFactory);
        final Player.Factory result;
        if (match.matches()) {
            final var type = match.group(1);
            final var param = Integer.parseInt(match.group(2));

            result = switch (type) {
                case "a" -> AI.factory(param);
                case "c" -> TwitchViewers.factory(messageDispatcher, param);
                default -> null;
            };
        } else {
            result = null;
        }

        if (result == null) {
            throw new CommandException("Invalid parameter for Connect4 player factory: " + encodedFactory);
        }
        return result;

    }

}
