package perobobbot.puckwar.physic;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.UnaryOperator1;
import perobobbot.common.math.Vector2D;

import java.awt.*;

@RequiredArgsConstructor
@Getter
public class Throw {

    public static final int PUCK_SIZE = 20;
    public static final Color COLOR = Color.WHITE;

    private final @NonNull User thrower;
    private final @NonNull Vector2D velocity;

    public Puck createPuck(@NonNull Vector2D initialPosition, int puckSize) {
        return new Puck(initialPosition, velocity, puckSize, thrower.getUseColor().orElse(COLOR));
    }

    public @NonNull Throw modifyVelocity(@NonNull UnaryOperator1<Vector2D> velocityModifier) {
        return new Throw(thrower,velocityModifier.apply(velocity));
    }
}
