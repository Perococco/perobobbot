package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.User;
import perobobbot.physics.Entity2DBase;
import perobobbot.rendering.Renderer;

import java.awt.*;
import java.time.Instant;

public class Puck extends Entity2DBase {

    public static final double DEFAULT_FRICTION_FACTOR = Math.log(2);

    public static Puck create(@NonNull User thrower, @NonNull Instant throwInstant, int radius) {
        final var puck = new Puck(thrower,throwInstant,radius);
        puck.setSlowDownCoefficient(DEFAULT_FRICTION_FACTOR);
        return puck;
    }

    @Getter
    private final @NonNull User thrower;

    @Getter
    private final @NonNull Instant throwInstant;

    private @NonNull Color color;

    @Getter
    private int radius;

    private double bending = 0;

    private Puck(
            @NonNull User thrower,
            @NonNull Instant throwInstant,
            int puckRadius) {
        this.thrower = thrower;
        this.throwInstant = throwInstant;
        this.color = thrower.getUserColor(Color.WHITE);
        this.radius = puckRadius;
    }

    public void drawWith(@NonNull Renderer renderer) {
        final double xc = getPosition().getX();
        final double yc = getPosition().getY();
        renderer.translate(xc, yc);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-xc, -yc);
    }

    public void updateBending() {
        final var v1 = getPreviousDynamic().getVelocity();
        final var v2  = getDynamic().getVelocity();
        final var n1 = v1.norm();
        final var n2 = v2.norm();
        if (n1<2 || n2 <2) {
            return;
        }
        final var cosAngle = Math.min(1, Math.max(-1, (v1.getX()*v2.getX()+v1.getY()*v2.getY())/(n1*n2)));

        this.bending+= Math.abs(Math.toDegrees(Math.acos(cosAngle)));
    }

    public double getBending() {
        return bending;
    }
}
