package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.User;
import perobobbot.common.math.MVector2D;
import perobobbot.common.math.Vector2D;
import perobobbot.rendering.Renderer;

import java.awt.*;
import java.time.Instant;

public class Puck {

    public static final double SPEED_THRESHOLD = 1e-3;
    public static final double DEFAULT_FRICTION_FACTOR = Math.log(2);

    private double frictionFactor = DEFAULT_FRICTION_FACTOR;

    @Getter
    private final @NonNull User thrower;

    @Getter
    private final @NonNull Instant throwInstant;

    @Getter
    private final MVector2D position = new MVector2D();

    private final MVector2D velocity = new MVector2D();

    private @NonNull Color color;

    @Getter
    private int radius;

    private boolean stopped;

    public Puck(
            @NonNull User thrower,
            @NonNull Instant throwInstant,
            @NonNull Vector2D position,
            @NonNull Vector2D velocity,
            int puckRadius) {
        this.thrower = thrower;
        this.throwInstant = throwInstant;
        this.color = thrower.getUserColor(Color.WHITE);
        this.position.setTo(position);
        this.velocity.setTo(velocity);
        this.radius = puckRadius;
        this.color = color;
    }

    public @NonNull Puck update(double dt) {
        if (stopped) {
            return this;
        }
        final double factor = Math.exp(-frictionFactor * dt);
        this.position.addScaled(velocity, dt);
        this.velocity.scale(factor);
        if (this.velocity.manhattanNorm() < SPEED_THRESHOLD) {
            stopped = true;
            this.velocity.nullify();
        }
        return this;
    }

    public void drawWith(@NonNull Renderer renderer) {
        final double xc = position.x();
        final double yc = position.y();
        renderer.translate(xc, yc);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-xc, -yc);
    }

}
