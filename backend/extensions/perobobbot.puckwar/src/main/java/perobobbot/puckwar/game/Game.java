package perobobbot.puckwar.game;

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
        final var initialPosition = Vector2D.of(0, overlaySize.getHeight() * 0.5);
        return new Game(puckSize,
                        initialPosition,
                        v -> v.scale(5),
                        new OutsiderPredicate(overlaySize));
    }

    /**
     * The default size of the pucks
     */
    private final @NonNull int puckSize;

    /**
     * The initial position of all pucks
     */
    private final @NonNull Vector2D initialPosition;

    /**
     * A modifier of the velocity so that the positive velocity are directed to the center of the overlay
     */
    private final @NonNull UnaryOperator1<Vector2D> velocityModifier;

    /**
     * Predicate to test if a puck is outside of the overlay drawing region
     */
    private final @NonNull Predicate<Puck> isOutsideGameRegion;

    /**
     * The throw that are not included in the game yet
     */
    private final BlockingDeque<Throw> pendingThrows = new LinkedBlockingDeque<>();

    /**
     * The list of active puck in the game
     */
    private final List<Puck> pucks = new ArrayList<>(256);


    /**
     * add a throw that will be added at the next rendering frame
     *
     * @param puckThrow the information about the throw
     */
    public void addThrow(@NonNull Throw puckThrow) {
        this.pendingThrows.add(puckThrow.modifyVelocity(velocityModifier));
    }

    /**
     * Update the position of the pucks in the game
     *
     * @param dt the delta in time since the last update
     */
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
        if (pendingThrows.isEmpty()) {
            return;
        }
        final List<Throw> retrievedPuckThrows = new ArrayList<>(pendingThrows.size() + 10);
        pendingThrows.drainTo(retrievedPuckThrows);
        retrievedPuckThrows.stream()
                           .map(thrw -> thrw.createPuck(initialPosition, puckSize))
                           .forEach(pucks::add);
    }

    public void draw(OverlayRenderer overlayRenderer) {
        pucks.forEach(p -> p.drawWith(overlayRenderer));
    }

    public void dispose() {
    }

}
