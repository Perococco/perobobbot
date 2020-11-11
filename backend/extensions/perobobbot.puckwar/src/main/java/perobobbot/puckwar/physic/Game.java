package perobobbot.puckwar.physic;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.UnaryOperator1;
import perobobbot.common.math.Vector2D;
import perobobbot.overlay.api.OverlayRenderer;
import perobobbot.overlay.api.OverlaySize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class Game {

    public static @NonNull Game create(@NonNull OverlaySize overlaySize, int puckSize) {
        final var initialPosition = Vector2D.of(0, overlaySize.getHeight()*0.5);
        final UnaryOperator1<Vector2D> velocityModifier = v -> v.scale(5);
        return new Game(puckSize,initialPosition,velocityModifier,new OutsiderPredicate(overlaySize));
    }

    private final @NonNull int puckSize;

    private final @NonNull Vector2D initialPosition;

    private final @NonNull UnaryOperator1<Vector2D> velocityModifier;

    private final @NonNull Predicate<Puck> isOutsideGameRegion;

    private final List<Puck> pucks = new ArrayList<>(256);

    private final BlockingDeque<Throw> pendingThrow = new LinkedBlockingDeque<>();


    public void addThrow(@NonNull Throw puckThrow) {
        this.pendingThrow.add(puckThrow.modifyVelocity(velocityModifier));
    }

    public void updateGame(double dt) {
        this.updatePuckPositions(dt);
        this.removeOutsiders();
        this.addPendingThrowToPuckList();
    }

    private void updatePuckPositions(double dt) {
        this.pucks.forEach(p -> p.update(dt));
    }

    private void removeOutsiders() {
        this.pucks.removeIf(isOutsideGameRegion);
    }

    private void addPendingThrowToPuckList() {
        if (pendingThrow.isEmpty()) {
            return;
        }
        final List<Throw> puckThrows = new ArrayList<>(pendingThrow.size()+10);
        pendingThrow.drainTo(puckThrows);
        puckThrows.stream()
                  .map(t -> t.createPuck(initialPosition, puckSize))
                  .forEach(pucks::add);
    }


    public void draw(OverlayRenderer overlayRenderer) {
        pucks.forEach(p -> p.drawWith(overlayRenderer));
    }

    public void dispose() {}

}
