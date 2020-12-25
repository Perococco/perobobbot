package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Dynamic implements RODynamic {

    private final @NonNull Entity2D target;

    @Getter
    private final @NonNull Vector2D position = Vector2D.create();
    @Getter
    private final @NonNull Vector2D velocity = Vector2D.create();
    @Getter
    private final @NonNull Accelerations accelerations;

    @Getter
    @Setter
    private double angle;

    @Getter @Setter
    private double angularSpeed;

    public Dynamic(@NonNull Entity2D target) {
        this.target = target;
        this.accelerations = new Accelerations(target);
    }

    public void setTo(@NonNull RODynamic other) {
        this.position.setTo(other.getPosition());
        this.velocity.setTo(other.getVelocity());
        this.accelerations.setTo(other.getAccelerations());
    }

    public void updateVelocityAndPosition(double dt) {
        if (!this.target.isFixed()) {
            velocity.addScaled(accelerations.getGravitationAcceleration(), dt)
                    .addScaled(accelerations.getFrictionAcceleration(), dt);

            position.addScaled(velocity, dt);
        }

        angle+=angularSpeed*dt;
    }
}
