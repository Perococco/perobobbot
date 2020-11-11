package perobobbot.common.math;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@RequiredArgsConstructor
public class Vector2D implements Vector2DInterface<Vector2D> {

    public static @NonNull Vector2D of(double x, double y) {
        return new Vector2D(x,y);
    }

    private final double x;
    private final double y;
    private final AtomicReference<Norm> norm = new AtomicReference<>(null);

    @Override
    public @NonNull Vector2D fix() {
        return this;
    }

    @Override
    public Vector2D normalize() {
        final var norm = norm();
        final var result = of(x/norm,y/norm);
        result.norm.set(Norm.ONE);
        return result;
    }

    @Override
    public @NonNull Vector2D negate() {
        return of(-x,-y);
    }

    @Override
    public @NonNull MVector2D unfix() {
        return new MVector2D(x, y);
    }

    @Override
    public double squaredNorm() {
        return getNorm().squaredNorm;
    }

    @Override
    public double manhattanNorm() {
        return Math.abs(x)+Math.abs(y);
    }

    @Override
    public double norm() {
        return getNorm().norm;
    }

    private @NonNull Norm getNorm() {
        return norm.updateAndGet(n -> n == null?new Norm(x,y):n);
    }

    @Override
    public @NonNull Vector2D add(double x, double y) {
        return of(this.x + x,this.y + y);
    }

    @Override
    public @NonNull Vector2D subtract(double x, double y) {
        return of(this.x - x,this.y - y);
    }

    @Override
    public @NonNull Vector2D addScaled(double x, double y, double scaled) {
        return of(this.x + scaled*x, this.y + scaled*y);
    }

    @Override
    public @NonNull Vector2D scale(double factor) {
        return of(x*factor, y*factor);
    }

    @Value
    private static class Norm {
        public static final Norm ONE = new Norm(1, 0);

        double squaredNorm;
        double norm;

        public Norm(double x, double y) {
            this.squaredNorm = x*x+y*y;
            this.norm = Math.sqrt(this.squaredNorm);
        }

    }
}
