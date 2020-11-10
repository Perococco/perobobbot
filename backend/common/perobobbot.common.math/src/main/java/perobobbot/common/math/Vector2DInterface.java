package perobobbot.common.math;

import lombok.NonNull;

public interface Vector2DInterface<V extends Vector2DInterface<V>> extends Point2D {

    double x();
    double y();

    double squaredNorm();
    double norm();
    double manhattanNorm();

    V normalize();

    @NonNull Vector2D fix();
    @NonNull MVector2D unfix();
    @NonNull V add(@NonNull Vector2DInterface<?> rhs);
    @NonNull V subtract(@NonNull Vector2DInterface<?> rhs);
    @NonNull V addScaled(@NonNull Vector2DInterface<?> rhs, double scaled);
    @NonNull V scale(double factor);
}
