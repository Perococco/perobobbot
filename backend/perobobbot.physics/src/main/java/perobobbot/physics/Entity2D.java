package perobobbot.physics;

import lombok.NonNull;

import java.util.Optional;

public interface Entity2D  extends ROEntity2D {

    Optional<Object> getUserData();

    void setUserData(@NonNull Object userData);

    default <T> Optional<T> getUserData(@NonNull Class<T> type) {
        return getUserData().filter(type::isInstance).map(type::cast);
    }


    @NonNull Dynamic getPreviousDynamic();

    @NonNull Dynamic getDynamic();

    void setBoundingBox(@NonNull BoundingBox2D boundingBox2D);

    void setSlowDownCoefficient(double slowDownCoefficient);

    void setName(@NonNull String name);

    void setMass(double mass);

    void setFixed(boolean fixed);

    void setGravityEffect(@NonNull GravityEffect gravityEffect);

    void setAccelerationsModifier(@NonNull AccelerationsModifier accelerationsModifier);

    @NonNull Accelerations getAccelerations();

    @NonNull Vector2D getPosition();

    @NonNull Vector2D getVelocity();

    void setAngle(double angle);

    void setAngularSpeed(double angularSpeed);
}
