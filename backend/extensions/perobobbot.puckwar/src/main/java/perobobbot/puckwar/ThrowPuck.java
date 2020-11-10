package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.CastTool;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.math.Vector2D;

import java.util.OptionalInt;

@RequiredArgsConstructor
public class ThrowPuck implements Consumer1<ExecutionContext> {

    private final @NonNull PuckWarExtension extension;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        if (!extension.isEnabled()) {
            return;
        }

        final String[] tokens = executionContext.getParameters().split(" +");
        if (tokens.length != 2) {
            return;
        }
        final OptionalInt vx = CastTool.castToInt(tokens[0]);
        final OptionalInt vy = CastTool.castToInt(tokens[1]);



        if (vx.isPresent() && vy.isPresent()) {
            extension.throwPuck(Vector2D.of(vx.getAsInt(),vy.getAsInt()));
        }
    }
}
