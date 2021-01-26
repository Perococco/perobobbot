package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.Value;
import perobobbot.physics.ROVector2D;
import perobobbot.physics.Vector2D;

import java.awt.*;

@Value
public class Token {

    private static final ROVector2D ACCELERATION = Vector2D.create(0,5000);

    @NonNull TokenType type;

    @NonNull int radius;

    @NonNull Vector2D position;

    @NonNull Vector2D velocity = Vector2D.create(0,0);

    @NonNull Vector2D finalPosition;

    public void computeNewPosition(double dt) {
        velocity.addScaled(ACCELERATION,dt);
        position.addScaled(velocity,dt);
        position.setY(Math.min(position.getY(), finalPosition.getY()));
    }

    public @NonNull Color getColor() {
        return type.getColor();
    }
}
