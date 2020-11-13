package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.*;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;
import perobobbot.math.Vector2D;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.game.Throw;

import java.time.Instant;
import java.util.Optional;
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
        final Optional<Double> vx = parse(tokens,0,CastTool::castToDouble);
        final Optional<Double> vy = parse(tokens,1,CastTool::castToDouble);

        final var thrower = transformUser(executionContext.getMessageOwner());
        if (vx.isPresent() && vy.isPresent()) {
            final var velocity = Vector2D.of(vx.get(),vy.get());

            final var throwInstant = executionContext.getReceptionTime();
            final var puckThrow = new Throw(thrower,throwInstant,velocity);
            extension.getCurrentGame().ifPresent(g -> g.addThrow(puckThrow));
        } else if (thrower.getPlatform() == Platform.LOCAL) {
            extension.getCurrentGame().ifPresent(g -> g.addThrow(new Throw(thrower, executionContext.getReceptionTime(), Vector2D.of(Double.NaN, Double.NaN))));
        }

    }

    private @NonNull User transformUser(@NonNull User user) {
        return user;
    }

    private <T> @NonNull Optional<T> parse(String[] token, int index, Function1<? super String, ? extends Optional<T>> mapper) {
        if (token.length<=index) {
            return Optional.empty();
        }
        return mapper.apply(token[index]);
    }

}
