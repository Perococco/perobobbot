package perobobbot.physics;

import lombok.NonNull;

public interface RODynamic {

    @NonNull ROVector2D getPosition();

    @NonNull ROVector2D getVelocity();

    @NonNull ROAccelerations getAccelerations();

    double getAngle();

    double getAngularSpeed();

    default double distanceTo(@NonNull RODynamic dynamic) {
        return getPosition().normOfDifference(dynamic.getPosition());
    }
}
