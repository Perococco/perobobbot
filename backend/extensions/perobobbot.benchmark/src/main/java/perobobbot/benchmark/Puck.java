package perobobbot.benchmark;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.MathTool;
import perobobbot.math.MVector2D;
import perobobbot.math.Vector2D;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;

import java.awt.*;

public class Puck {

    @Getter @Setter
    private int radius;
    @Getter @Setter
    private Color color = Color.RED;
    private final MVector2D position = MVector2D.of(0,0);
    private final MVector2D velocity = MVector2D.of(0,0);

    public Puck(@NonNull Vector2D position,@NonNull Vector2D velocity) {
        this.position.setTo(position);
        this.velocity.setTo(velocity);
    }
    public Puck(@NonNull Vector2D position,@NonNull Vector2D velocity, @NonNull Color color, int radius) {
        this(position,velocity);
        this.color = color;
        this.radius = radius;
    }

    public Puck update(double dt) {
        this.position.addScaled(velocity,dt);
        return this;
    }

    public Puck wrap(Size overlaySize) {
        final var xm = modulate(this.position.x(),overlaySize.getWidth());
        final var ym = modulate(this.position.y(),overlaySize.getHeight());
        this.position.setX(xm);
        this.position.setY(ym);
        return this;
    }

    private double modulate(double p, int size) {
        return MathTool.mod(p+radius,size+2*radius)-radius;
    }

    public Puck draw(@NonNull Renderer renderer) {
        final var x = position.x();
        final var y = position.y();
        renderer.translate(x,y);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-x,-y);
        return this;
    }
}
