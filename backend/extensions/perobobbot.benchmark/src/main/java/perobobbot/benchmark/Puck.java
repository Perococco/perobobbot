package perobobbot.benchmark;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.MathTool;
import perobobbot.physics.ImmutableVector2D;
import perobobbot.physics.Vector2D;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;

import java.awt.*;

public class Puck {

    @Getter @Setter
    private int radius;
    @Getter @Setter
    private Color color = Color.RED;
    private final Vector2D position = Vector2D.create();
    private final Vector2D velocity = Vector2D.create();

    public Puck(@NonNull ImmutableVector2D position, @NonNull ImmutableVector2D velocity) {
        this.position.setTo(position);
        this.velocity.setTo(velocity);
    }
    public Puck(@NonNull ImmutableVector2D position, @NonNull ImmutableVector2D velocity, @NonNull Color color, int radius) {
        this(position,velocity);
        this.color = color;
        this.radius = radius;
    }

    public Puck update(double dt) {
        this.position.addScaled(velocity,dt);
        return this;
    }

    public Puck wrap(Size overlaySize) {
        final var xm = modulate(this.position.getX(),overlaySize.getWidth());
        final var ym = modulate(this.position.getY(),overlaySize.getHeight());
        this.position.setX(xm);
        this.position.setY(ym);
        return this;
    }

    private double modulate(double p, int size) {
        return MathTool.mod(p+radius,size+2*radius)-radius;
    }

    public Puck draw(@NonNull Renderer renderer) {
        final var x = position.getX();
        final var y = position.getY();
        renderer.translate(x,y);
        renderer.setColor(color);
        renderer.fillCircle(0, 0, radius);
        renderer.translate(-x,-y);
        return this;
    }
}
