package perobobbot.physics;

import lombok.NonNull;

public interface ROVector2D {

    double getX();

    double getY();

    double squaredNorm();

    double norm();

    double manhattanNorm();

    default double normOfDifference(@NonNull ROVector2D ROVector2D) {
        final var dx = getX()- ROVector2D.getX();
        final var dy = getY()- ROVector2D.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }


}
