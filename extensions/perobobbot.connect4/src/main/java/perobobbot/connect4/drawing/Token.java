package perobobbot.connect4.drawing;

import lombok.NonNull;
import lombok.Value;
import perobobbot.physics.ROVector2D;
import perobobbot.physics.Vector2D;

import java.awt.*;

@Value
public class Token {

    private static final ROVector2D ACCELERATION = Vector2D.create(0,1);

    @NonNull TokenType type;

    @NonNull int radius;

    @NonNull Vector2D position;

    @NonNull Vector2D velocity = Vector2D.create(0,0);

    @NonNull Vector2D finalPosition;

    public void computeNewPosition(double dt) {
        final var acceleration = Vector2D.create(0,5000);
        velocity.addScaled(acceleration,dt);
        position.addScaled(velocity,dt);
        position.setY(Math.min(position.getY(), finalPosition.getY()));
    }

    public @NonNull Color getColor() {
        return type.getColor();
    }
}
