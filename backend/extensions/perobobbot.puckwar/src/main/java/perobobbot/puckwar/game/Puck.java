package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.User;
import perobobbot.math.ImmutableVector2D;
import perobobbot.math.Vector2D;
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
    private final Vector2D position = Vector2D.create();

    private final Vector2D velocity = Vector2D.create();

    private @NonNull Color color;

    @Getter
    private int radius;

    private boolean stopped;

    private boolean dead = false;

    public Puck(
            @NonNull User thrower,
            @NonNull Instant throwInstant,
            @NonNull ImmutableVector2D position,
            @NonNull ImmutableVector2D velocity,
            int puckRadius) {
        this.thrower = thrower;
        this.throwInstant = throwInstant;
        this.color = thrower.getUserColor(Color.WHITE);
        this.position.setTo(position);
        this.velocity.setTo(velocity);
        this.radius = puckRadius;
    }

    public @NonNull Puck update(double dt, @NonNull BlackHole backHole, @NonNull Target target) {
        if (stopped || dead) {
            return this;
        }
        this.position.addScaled(velocity, dt);

        final Vector2D forces = computeForces(dt,backHole,target);

        //v = v*friction
        this.velocity.addScaled(forces,dt);
        if (this.velocity.manhattanNorm() < SPEED_THRESHOLD) {
            stopped = true;
            this.velocity.nullify();
        }
        return this;
    }

    private @NonNull Vector2D computeForces(double dt, @NonNull BlackHole blackHole, @NonNull Target target) {
        final var friction = Math.exp(-frictionFactor * dt);

        final Vector2D forces = this.velocity.duplicate().scale((friction-1)/dt);

        if (target.isPointInside(position)) {
            return forces;
        }

        final var vector = blackHole.getPosition().subtract(this.position);

        if (vector.norm()<5) {
            this.dead = true;
            return forces;
        } else {
            // GMm/r^2
            forces.addScaled(vector.normalize(),5e6/vector.squaredNorm());
        }

        return forces;
    }


    public void drawWith(@NonNull Renderer renderer) {
        final double xc = position.x();
        final double yc = position.y();
        renderer.translate(xc, yc);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-xc, -yc);
    }

    public boolean isACheater() {
        return Double.isNaN(velocity.x());
    }

    public void clearVelocity() {
        this.velocity.nullify();
        this.stopped = true;
    }
}
