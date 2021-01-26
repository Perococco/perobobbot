package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class ImmutableVector2D implements ROVector2D {

    public static @NonNull ImmutableVector2D cartesian(double x, double y) {
        return new ImmutableVector2D(x,y);
    }

    public static @NonNull ImmutableVector2D radialInDegree(double norm, double angleInDegree) {
        return radial(norm,Math.toRadians(angleInDegree));
    }

    public static @NonNull ImmutableVector2D radial(double norm, double angle) {
        final double x= norm*Math.cos(angle);
        final double y= norm*Math.sin(angle);
        final var result = ImmutableVector2D.cartesian(x,y);
        result.norm.set(Math.abs(norm));
        return result;
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
    public double normOfDifference(@NonNull ROVector2D ROVector2D) {
        final var dx = this.getX() - ROVector2D.getX();
        final var dy = this.getY() - ROVector2D.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }

    public @NonNull ImmutableVector2D subtract(@NonNull ROVector2D position) {
        return cartesian(x-position.getX(),y-position.getY());
    }

    public @NonNull ImmutableVector2D subtract(double x, double y) {
        return cartesian(this.x-x,this.y-y);
    }

    public @NonNull ImmutableVector2D normalize() {
        final double norm = norm();
        return cartesian(x/norm,y/norm);
    }

    public @NonNull ImmutableVector2D scale(double scale) {
        return cartesian(x*scale,y*scale);
    }
}
