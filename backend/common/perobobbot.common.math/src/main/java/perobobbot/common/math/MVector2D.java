package perobobbot.common.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MVector2D implements Vector2DInterface<MVector2D> {

    public static MVector2D of(double x, double y) {
        return new MVector2D(x, y);
    }

    private double x;
    private double y;

    @Override
    public @NonNull Vector2D fix() {
        return Vector2D.of(x,y);
    }

    @Override
    public @NonNull MVector2D unfix() {
        return of(x,y);
    }

    @Override
    public double squaredNorm() {
        return x*x+y*y;
    }

    @Override
    public double norm() {
        return Math.sqrt(x*x+y*y);
    }

    @Override
    public double manhattanNorm() {
        return Math.abs(x)+Math.abs(y);
    }

    @Override
    public @NonNull MVector2D normalize() {
        final var norm = norm();
        this.x/=norm;
        this.y/=norm;
        return this;
    }

    public @NonNull MVector2D setTo(@NonNull Vector2DInterface<?> source) {
        this.x = source.x();
        this.y = source.y();
        return this;
    }

    @Override
    public @NonNull MVector2D add(@NonNull Vector2DInterface<?> rhs) {
        this.x+=rhs.x();
        this.y+=rhs.y();
        return this;
    }

    @Override
    public @NonNull MVector2D subtract(@NonNull Vector2DInterface<?> rhs) {
        this.x-=rhs.x();
        this.y-=rhs.y();
        return this;
    }

    @Override
    public @NonNull MVector2D addScaled(@NonNull Vector2DInterface<?> rhs, double scaled) {
        this.x+=scaled*rhs.x();
        this.y+=scaled*rhs.y();
        return this;
    }

    @Override
    public @NonNull MVector2D scale(double factor) {
        this.x*=factor;
        this.y*=factor;
        return this;
    }

    public void nullify() {
        this.x = 0;
        this.y = 0;
    }
}
