package perobobbot.physics;

import lombok.NonNull;


public interface ROAccelerations {

    /**
     * The entity the forces apply to
     */
    @NonNull ROEntity2D getTarget();

    @NonNull ROVector2D getGravitationAcceleration();

    @NonNull ROVector2D getFrictionAcceleration();
}
