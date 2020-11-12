package perobobbot.common.math;

import lombok.NonNull;

public interface Vector2DInterface<V extends Vector2DInterface<V>> extends Point2D {

    double x();
    double y();

    double squaredNorm();
    double norm();
    double manhattanNorm();

    V normalize();

    @NonNull V negate();
    @NonNull Vector2D fix();
    @NonNull MVector2D unfix();
    @NonNull V add(double x, double y);
    @NonNull V subtract(double x, double y);
    @NonNull V addScaled(double x, double y, double scaled);
    @NonNull V scale(double factor);

    default @NonNull V add(@NonNull Vector2DInterface<?> rhs) {
        return add(rhs.x(),rhs.y());
    }

    default @NonNull V subtract(@NonNull Vector2DInterface<?> rhs) {
        return subtract(rhs.x(),rhs.y());
    }

    default @NonNull V addScaled(@NonNull Vector2DInterface<?> rhs, double scaled) {
        return addScaled(rhs.x(),rhs.y(),scaled);
    }


    default double distanceTo(MVector2D position) {
        final var dx = this.x() - position.x();
        final var dy = this.y() - position.y();
        return Math.sqrt(dx*dx+dy*dy);
    }

}
