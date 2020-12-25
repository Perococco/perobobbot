package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class Accelerations implements ROAccelerations {

    @Getter
    private final @NonNull ROEntity2D target;

    @Getter
    private final @NonNull Vector2D gravitationAcceleration = Vector2D.create();

    @Getter
    private final @NonNull Vector2D frictionAcceleration = Vector2D.create();

    public void setTo(@NonNull ROAccelerations other) {
        this.gravitationAcceleration.setTo(other.getGravitationAcceleration());
        this.frictionAcceleration.setTo(other.getFrictionAcceleration());
    }
}
