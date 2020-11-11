package perobobbot.puckwar.physic;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.math.MVector2D;
import perobobbot.common.math.Vector2D;
import perobobbot.overlay.api.OverlayRenderer;

import java.awt.*;

public class Puck {

    public static final double SPEED_THRESHOLD = 1e-3;
    public static final double DEFAULT_FRICTION_FACTOR = Math.log(2);

    private double frictionFactor = DEFAULT_FRICTION_FACTOR;
    @Getter
    private final MVector2D position = new MVector2D();
    private final MVector2D velocity = new MVector2D();
    @Getter
    private int radius;
    private double hsize;
    private Color color;

    private boolean stopped;

    public Puck(@NonNull Vector2D position, @NonNull Vector2D velocity, int puckRadius, Color color) {
        this.position.setTo(position);
        this.velocity.setTo(velocity);
        this.radius = puckRadius;
        this.hsize = this.radius * 0.5;
        this.color = color;
    }

    public @NonNull Puck update(double dt) {
        if (stopped) {
            return this;
        }
        final double factor = Math.exp(-frictionFactor*dt);
        this.position.addScaled(velocity,dt);
        this.velocity.scale(factor);
        if (this.velocity.manhattanNorm()<SPEED_THRESHOLD) {
            stopped = true;
            this.velocity.nullify();
        }
        return this;
    }

    public void drawWith(@NonNull OverlayRenderer renderer) {
        final double xc = position.x();
        final double yc = position.y();
        renderer.translate(xc,yc);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-xc,-yc);
    }

}
