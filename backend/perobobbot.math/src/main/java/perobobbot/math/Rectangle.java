package perobobbot.math;

import lombok.Value;

@Value
public class Rectangle {

    double x;
    double y;
    double width;
    double height;

    public boolean contains(Point2D position) {
        final var px = position.x();
        final var py = position.y();

        return px>=x && py>=y && (px-x)<=width && (py-y)<=height;
    }
}
