package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.*;
import perobobbot.lang.fp.Consumer1;
import perobobbot.math.Vector2D;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.game.Throw;

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
            final var thrower = transformUser(executionContext.getMessageOwner());

            final var throwInstant = executionContext.getReceptionTime();
            final var puckThrow = new Throw(thrower,throwInstant,velocity);
            extension.getCurrentGame().ifPresent(g -> g.addThrow(puckThrow));
        }

    }

    private @NonNull User transformUser(@NonNull User user) {
        return user;
    }
}
