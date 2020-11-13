package perobobbot.math;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class ImmutableVector2D implements ReadOnlyVector2D {

    public static @NonNull ImmutableVector2D of(double x, double y) {
        return new ImmutableVector2D(x,y);
    }

    @Getter
    private final double x;
    @Getter
    private final double y;

    private final AtomicReference<Double> normSquared = new AtomicReference<>(null);
    private final AtomicReference<Double> norm = new AtomicReference<>(null);;
    private final AtomicReference<Double> manhattanNorm = new AtomicReference<>(null);;


    @Override
    public double squaredNorm() {
        return normSquared.updateAndGet(d -> d==null?x*x+y*y:d);
    }

    @Override
    public double norm() {
        return norm.updateAndGet(d -> d==null?Math.sqrt(squaredNorm()):d);
    }

    @Override
    public double manhattanNorm() {
        return manhattanNorm.updateAndGet(d -> d==null?Math.abs(x)+Math.abs(y):d);
    }

    @Override
    public double normOfDifference(@NonNull ReadOnlyVector2D readOnlyVector2D) {
        final var dx = this.x() - readOnlyVector2D.x();
        final var dy = this.y() - readOnlyVector2D.y();
        return Math.sqrt(dx*dx+dy*dy);
    }

    public @NonNull ImmutableVector2D subtract(@NonNull ReadOnlyVector2D position) {
        return of(x-position.x(),y-position.y());
    }

    public @NonNull ImmutableVector2D subtract(double x, double y) {
        return of(this.x-x,this.y-y);
    }

    public @NonNull ImmutableVector2D normalize() {
        final double norm = norm();
        return of(x/norm,y/norm);
    }

    public @NonNull ImmutableVector2D scale(double scale) {
        return of(x*scale,y*scale);
    }
}
