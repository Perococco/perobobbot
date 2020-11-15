package perobobbot.physics;

import lombok.Value;

@Value
public class Rectangle {

    double x;
    double y;
    double width;
    double height;

    public boolean contains(Vector2D position) {
        final var px = position.getX();
        final var py = position.getY();

        return px>=x && py>=y && (px-x)<=width && (py-y)<=height;
    }
}
