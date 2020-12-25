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

    default double scalarProduct(@NonNull ROVector2D vector2D) {
        return this.getX()*vector2D.getX()+this.getY()*vector2D.getY();
    }

    default double angleBetween(@NonNull ROVector2D vector2D) {
        final var n1 = this.norm();
        final var n2 = vector2D.norm();
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        final var cosAngle = scalarProduct(vector2D) / (n1 * n2);
        if (cosAngle <= -1) {
            return -Math.PI;
        } else if (cosAngle >= 1) {
            return Math.PI;
        } else {
            return Math.acos(cosAngle);
        }
    }

}
