package perococco.perobobbot.physics;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.physics.*;

import java.util.*;

@Log4j2
public class PeroUniverse implements Universe {

    public static final double GRAVITATIONAL_CONSTANT = 6.6743015e-11;

    private final Map<UUID, E> entities = new HashMap<>();

    private final Set<UUID> gravityActors = new HashSet<>();
    private final Set<UUID> forcesModifiers = new HashSet<>();

    private final Listener listener = new Listener();

    public void addEntity(@NonNull Entity2D entity2D) {
        final var id = entity2D.getId();
        if (entities.containsKey(id)) {
            LOG.warn("Trying to add an entity with same id");
            return;
        }
        final var e = new E(entity2D);
        this.entities.put(id, e);

        if (entity2D.getAccelerationsModifier() != AccelerationsModifier.NOP) {
            forcesModifiers.add(id);
        }
        if (entity2D.getGravityEffect() != GravityEffect.NONE) {
            gravityActors.add(id);
        }
        e.addListener();
    }

    @Override
    public void removeEntity(@NonNull UUID entityId) {
        final var entity = entities.remove(entityId);
        if (entity != null) {
            entity.removeListener();
            gravityActors.remove(entityId);
            forcesModifiers.remove(entityId);
        }
    }

    @Override
    public void update(double dt) {
        copyCurrentToPrevious();
        computeAccelerations(dt);
        updateVelocitiesAndPositions(dt);
    }

    private void copyCurrentToPrevious() {
        entities.values().forEach(e -> e.getPreviousDynamic().setTo(e.getDynamic()));
    }

    private void updateVelocitiesAndPositions(double dt) {
        entities.values().forEach(e -> e.getDynamic().updateVelocityAndPosition(dt));
    }

    private void computeAccelerations(double dt) {
        entities.values()
                .stream()
                .map(Entity2D::getAccelerations)
                .forEach(a -> computeAccelerations(a, dt));
    }

    private void computeAccelerations(@NonNull Accelerations accelerations, double dt) {
        final ROEntity2D target = accelerations.getTarget();
        if (target.isFixed()) {
            return;
        }
        this.computeFrictionAcceleration(target, accelerations.getFrictionAcceleration(), dt);
        this.computeGravitationAcceleration(target, accelerations.getGravitationAcceleration());
        this.applyForceModifiers(accelerations);
    }

    private void applyForceModifiers(@NonNull Accelerations accelerations) {
        forcesModifiers.stream()
                       .map(entities::get)
                       .filter(m -> m.getBoundingBox().isInside(accelerations.getTarget().getPosition()))
                       .forEach(m -> {
                           m.getAccelerationsModifier().modifyAccelerations(accelerations);
                       });
    }

    private void computeFrictionAcceleration(@NonNull ROEntity2D target, Vector2D frictionAcceleration, double dt) {
        final double factor = Math.exp(-target.getSlowDownCoefficient()*dt);
        frictionAcceleration.setTo(target.getVelocity())
                            .scale((factor-1) / dt);
    }

    private void computeGravitationAcceleration(ROEntity2D target, Vector2D acceleration) {
        final Vector2D buffer = Vector2D.create();
        acceleration.nullify();
        gravityActors.stream()
                     .map(entities::get)
                     .forEach(e -> {
                         buffer.setTo(e.getPosition())
                               .subtract(target.getPosition());
                         final var distance3 = Math.max(1,buffer.norm()*buffer.squaredNorm());
                         final var factor = GRAVITATIONAL_CONSTANT * e.getMass() / distance3;
                         final var transformer = e.getGravityEffect().applyEffect(factor);
                         acceleration.addScaled(buffer, transformer);
                     });

    }

    private class Listener implements EntityListener {

        @Override
        public void onGravitationEffect(@NonNull UUID entityId, @NonNull GravityEffect oldEffect, @NonNull GravityEffect newEffect) {
            if (oldEffect == GravityEffect.NONE) {
                gravityActors.add(entityId);
            } else if (newEffect == GravityEffect.NONE) {
                gravityActors.remove(entityId);
            }
        }

        @Override
        public void onForceModifier(@NonNull UUID entityId, @NonNull AccelerationsModifier oldModifier, @NonNull AccelerationsModifier newModifier) {
            if (oldModifier == AccelerationsModifier.NOP) {
                forcesModifiers.add(entityId);
            } else if (newModifier == AccelerationsModifier.NOP) {
                forcesModifiers.remove(entityId);
            }
        }
    }

    private class E extends ProxyEntity2D {

        private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

        public E(@NonNull Entity2D entity2D) {
            super(entity2D);
        }

        public void addListener() {
            subscriptionHolder.replaceWith(() -> getDelegate().addListener(listener));
        }

        public void removeListener() {
            subscriptionHolder.unsubscribe();
        }

    }

}
