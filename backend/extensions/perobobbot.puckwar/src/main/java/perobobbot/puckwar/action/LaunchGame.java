package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.CastTool;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.game.GameOptions;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class LaunchGame implements Consumer1<ExecutionContext> {

    private final @NonNull PuckWarExtension puckWarExtension;

    @Override
    public void f(ExecutionContext executionContext) {
        final var gameOptions = parseGameOptions(executionContext.getParameters());
        puckWarExtension.startGame(gameOptions);
    }

    private @NonNull GameOptions parseGameOptions(@NonNull String parameters) {
        final var token = parameters.trim().split(" +");
        final var puckSize = parse(token,0,CastTool::castToInt, 20);
        final var duration = Duration.ofSeconds(parse(token,1,CastTool::castToLong,60l));

        return new GameOptions(puckSize,duration);
    }

    private <T> @NonNull T parse(String[] token, int index, Function1<? super String, ? extends Optional<T>> mapper,@NonNull T defaultValue) {
        if (token.length<=index) {
            return defaultValue;
        }
        return mapper.apply(token[index]).orElse(defaultValue);
    }
}
