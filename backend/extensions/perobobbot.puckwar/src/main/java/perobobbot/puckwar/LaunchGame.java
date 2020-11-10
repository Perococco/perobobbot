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

        final var puckSize = token.length>=1?CastTool.castToInt(token[0]).orElse(20):20;

        puckWarExtension.startGame(puckSize);
    }
}
