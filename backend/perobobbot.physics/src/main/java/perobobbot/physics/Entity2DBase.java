package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.Listeners;
import perobobbot.lang.Subscription;

import java.util.UUID;

public class Entity2DBase implements Entity2D {

    private final Listeners<EntityListener> listeners = new Listeners<>();

    @Getter private final UUID id = UUID.randomUUID();

    @Getter private final @NonNull Dynamic previousDynamic;
    @Getter private final @NonNull Dynamic dynamic;

    @Getter @Setter private BoundingBox2D boundingBox = BoundingBox2D.NONE;
    @Getter @Setter private double mass;
    @Getter @Setter private double slowDownCoefficient;
    @Getter @Setter private boolean fixed;
    @Getter @Setter private String name;
    @Getter private @NonNull GravityEffect gravityEffect = GravityEffect.NONE;
    @Getter private @NonNull AccelerationsModifier accelerationsModifier = AccelerationsModifier.NOP;

    public Entity2DBase() {
        this.previousDynamic = new Dynamic(this);
        this.dynamic = new Dynamic(this);
    }
    public Entity2DBase(@NonNull String name) {
        this();
        this.name = name;
    }

    @Override
    public void setGravityEffect(@NonNull GravityEffect gravityEffect) {
        final var oldGravityEffect = this.gravityEffect;
        if (oldGravityEffect == gravityEffect) {
            return;
        }
        this.gravityEffect = gravityEffect;
        listeners.warnListeners(l -> l.onGravitationEffect(id,oldGravityEffect,gravityEffect));
    }

    public void setAccelerationsModifier(@NonNull AccelerationsModifier accelerationsModifier) {
        final var oldForcesModifier = this.accelerationsModifier;
        if (oldForcesModifier == accelerationsModifier) {
            return;
        }
        this.accelerationsModifier = accelerationsModifier;
        listeners.warnListeners(l -> l.onForceModifier(id,oldForcesModifier, accelerationsModifier));
    }

    @Override
    public @NonNull Subscription addListener(@NonNull EntityListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull Accelerations getAccelerations() {
        return getDynamic().getAccelerations();
    }

    @Override
    public @NonNull Vector2D getPosition() {
        return getDynamic().getPosition();
    }

    @Override
    public @NonNull Vector2D getVelocity() {
        return getDynamic().getVelocity();
    }

    @Override
    public void setAngle(double angle) {
        getDynamic().setAngle(angle);
    }

    @Override
    public void setAngularSpeed(double angularSpeed) {
        getDynamic().setAngularSpeed(angularSpeed);
    }

    @Override
    public double getAngle() {
        return getDynamic().getAngle();
    }

    @Override
    public double getAngularSpeed() {
        return getDynamic().getAngularSpeed();
    }
}
