package perobobbot.puckwar;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.math.MVector2D;

import java.awt.*;

public class Puck {

    public static final double SPEED_THRESHOLD = 1e-3;
    public static final double DEFAULT_FRICTION_FACTOR = Math.log(2) / 3;

    private double frictionFactor = DEFAULT_FRICTION_FACTOR;
    @Getter
    private MVector2D position;
    private MVector2D velocity;
    private int size;
    private double hsize;
    private Color color;

    private boolean stopped;

    public Puck(@NonNull MVector2D position, @NonNull MVector2D velocity, int puckSize, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.size = puckSize;
        this.hsize = this.size*0.5;
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

    public void draw(@NonNull Graphics2D g2) {
        final double ofx = Math.round(position.x()-hsize);
        final double ofy = Math.round(position.y()-hsize);
        g2.translate(ofx,ofy);
        g2.setPaint(color);
        g2.fillOval(0,0, size, size);
        g2.translate(-ofx,-ofy);
    }

}
