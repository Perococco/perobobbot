package perobobbot.physics;

import lombok.NonNull;
import perobobbot.lang.Subscription;

import java.util.UUID;

public interface ROEntity2D {

    @NonNull UUID getId();

    @NonNull RODynamic getPreviousDynamic();

    @NonNull RODynamic getDynamic();

    @NonNull BoundingBox2D getBoundingBox();

    @NonNull String getName();

    double getSlowDownCoefficient();

    double getMass();

    boolean isFixed();

    @NonNull GravityEffect getGravityEffect();

    @NonNull AccelerationsModifier getAccelerationsModifier();

    @NonNull Subscription addListener(@NonNull EntityListener listener);

    default double distanceTo(@NonNull ROEntity2D other) {
        return this.getDynamic().distanceTo(other.getDynamic());
    }

    @NonNull ROVector2D getPosition();

    @NonNull ROVector2D getVelocity();

    double getAngle();

    double getAngularSpeed();
}
