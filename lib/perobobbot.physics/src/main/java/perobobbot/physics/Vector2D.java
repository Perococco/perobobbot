package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Vector2D implements ROVector2D {

    public static @NonNull Vector2D create(double x, double y) {
        return new Vector2D(x,y);
    }

    public static Vector2D create() {
        return new Vector2D(0,0);
    }

    private Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;


    public @NonNull Vector2D subtract(@NonNull ROVector2D other) {
        this.x -= other.getX();
        this.y -= other.getY();
        return this;
    }

    public @NonNull Vector2D setTo(@NonNull ROVector2D source) {
        this.x = source.getX();
        this.y = source.getY();
        return this;
    }

    public @NonNull Vector2D setToCartesian(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public @NonNull Vector2D addScaled(@NonNull ROVector2D ROVector2D, double scale) {
        this.x += ROVector2D.getX()*scale;
        this.y += ROVector2D.getY()*scale;
        return this;
    }

    public @NonNull Vector2D scale(double scale) {
        this.x*=scale;
        this.y*=scale;
        return this;
    }

    @Override
    public double squaredNorm() {
        return x*x+y*y;
    }

    @Override
    public double norm() {
        return Math.sqrt(squaredNorm());
    }

    @Override
    public double manhattanNorm() {
        return Math.abs(x)+Math.abs(y);
    }

    public @NonNull Vector2D nullify() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public @NonNull Vector2D duplicate() {
        return new Vector2D(this.x,this.y);
    }

}
