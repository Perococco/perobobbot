package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.CastTool;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;

@RequiredArgsConstructor
public class LaunchGame implements Consumer1<ExecutionContext> {

    private final @NonNull PuckWarExtension puckWarExtension;

    @Override
    public void f(ExecutionContext executionContext) {
        final var token = executionContext.getParameters().trim().split(" +");

        final var nbPucks = token.length>=1?CastTool.castToInt(token[0]).orElse(200):200;
        final var tuckSize = token.length>=2?CastTool.castToInt(token[1]).orElse(3):3;

        puckWarExtension.stopGame();
        puckWarExtension.startGame(nbPucks,tuckSize);
    }
}
