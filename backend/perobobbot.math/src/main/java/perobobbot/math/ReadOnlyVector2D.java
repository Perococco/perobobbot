package perobobbot.math;

import lombok.NonNull;

public interface ReadOnlyVector2D {

    double x();

    double y();

    double squaredNorm();

    double norm();

    double manhattanNorm();

    default double normOfDifference(@NonNull ReadOnlyVector2D readOnlyVector2D) {
        final var dx = x()-readOnlyVector2D.x();
        final var dy = y()-readOnlyVector2D.y();
        return Math.sqrt(dx*dx+dy*dy);
    }


}
