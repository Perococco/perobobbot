package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.User;
import perobobbot.lang.fp.UnaryOperator1;
import perobobbot.math.Vector2D;

import java.awt.*;
import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class Throw {

    public static final int PUCK_SIZE = 20;
    public static final Color COLOR = Color.WHITE;

    private final @NonNull User thrower;
    private final @NonNull Instant throwInstant;
    private final @NonNull Vector2D velocity;

    public Puck createPuck(@NonNull Vector2D initialPosition, int puckSize) {
        return new Puck(thrower,throwInstant,initialPosition, velocity, puckSize);
    }

    public @NonNull Throw modifyVelocity(@NonNull UnaryOperator1<Vector2D> velocityModifier) {
        return new Throw(thrower,throwInstant,velocityModifier.apply(velocity));
    }
}
