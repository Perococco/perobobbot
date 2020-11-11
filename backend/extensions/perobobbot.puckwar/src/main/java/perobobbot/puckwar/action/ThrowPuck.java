package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.CastTool;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.math.Vector2D;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.physic.Throw;

import java.util.OptionalDouble;

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
        final OptionalDouble vx = CastTool.castToDouble(tokens[0]);
        final OptionalDouble vy = CastTool.castToDouble(tokens[1]);

        if (vx.isPresent() && vy.isPresent()) {
            final var velocity = Vector2D.of(vx.getAsDouble(),vy.getAsDouble());
            final var puckThrow = new Throw(executionContext.getMessageOwner(),velocity);
            extension.getCurrentGame().ifPresent(g -> g.addThrow(puckThrow));
        }

    }
}
