package perobobbot.puckwar.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
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
public class ThrowPuck implements CommandAction {

    private final @NonNull PuckWarExtension extension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final Optional<Double> speed = parsing.findDoubleParameter("speed");
        final Optional<Double> angle = parsing.findDoubleParameter("angle");

        final var thrower = context.getMessageOwner();

        OptionalTools.map(speed, angle, ImmutableVector2D::radial)
                     .ifPresent(velocity -> {
                         final var throwInstant = context.getReceptionTime();
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
