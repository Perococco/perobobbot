package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.CastTool;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.OptionalTools;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;
import perobobbot.physics.ImmutableVector2D;
import perobobbot.puckwar.PuckWarExtension;
import perobobbot.puckwar.game.Throw;

import java.util.Optional;

@RequiredArgsConstructor
public class ThrowPuck implements Consumer1<ExecutionContext> {

    private final @NonNull PuckWarExtension extension;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        if (!extension.isEnabled()) {
            return;
        }

        final String[] tokens = executionContext.getParameters().split(" +");
        final Optional<Double> speed = parse(tokens, 0, CastTool::castToDouble);
        final Optional<Double> angle = parse(tokens, 1, CastTool::castToDouble).map(Math::toRadians);

        final var thrower = executionContext.getMessageOwner();

        OptionalTools.map(speed, angle, ImmutableVector2D::radial)
                     .ifPresent(velocity -> {
                         final var throwInstant = executionContext.getReceptionTime();
                         final var puckThrow = new Throw(thrower, throwInstant, velocity);
                         extension.getCurrentGame().ifPresent(g -> g.addThrow(puckThrow));
                     });
    }

    private <T> @NonNull Optional<T> parse(String[] token, int index, Function1<? super String, ? extends Optional<T>> mapper) {
        if (token.length <= index) {
            return Optional.empty();
        }
        return mapper.apply(token[index]);
    }

}
