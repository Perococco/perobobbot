package perobobbot.puckwar;

import lombok.NonNull;
import perobobbot.common.math.MVector2D;
import perobobbot.common.math.Rectangle;
import perobobbot.common.math.Vector2D;
import perobobbot.overlay.api.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PuckWarOverlay implements OverlayClient {

    private final int puckSize;

    private boolean nice = false;

    private List<Puck> pucks = new ArrayList<>(256);

    private List<Vector2D> pendingPuckVelocities = new ArrayList<>();

    public PuckWarOverlay(int puckSize) {
        this.puckSize = puckSize;
    }

    @Override
    public void initialize(@NonNull Overlay overlay) {
    }

    @Override
    public void dispose(@NonNull Overlay overlay) {
        pucks.clear();
    }

    public void setNice(boolean nice) {
        this.nice = nice;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final OverlayRenderer renderer = iteration.getOverlayRenderer();
        final double dt = iteration.getDeltaTime();

        this.updatePuckPositions(dt);
        this.addPendingPucks();
        this.cleanUpPucks(renderer.getOverlaySize());

        pucks.forEach(p -> p.drawWith(renderer));
    }

    private void addPendingPucks() {
        pendingPuckVelocities.stream()
                             .map(v -> new Puck(MVector2D.of(0, 0), v.unfix(), puckSize, Color.RED))
                             .forEach(p -> pucks.add(p));
        pendingPuckVelocities.clear();
    }

    private void updatePuckPositions(double dt) {
        pucks.forEach(p -> p.update(dt));
    }

    private void cleanUpPucks(@NonNull OverlaySize overlaySize) {
        pucks.removeIf(isPuckOutsideOverlay(overlaySize));
    }

    private Predicate<Puck> isPuckOutsideOverlay(@NonNull OverlaySize overlaySize) {
        final Rectangle overlay = new Rectangle(-puckSize, -puckSize, overlaySize.getWidth() + 2 * puckSize, overlaySize.getHeight() + 2 * puckSize);
        return puck -> !overlay.contains(puck.getPosition());
    }

    public void throwPuck(@NonNull Vector2D speed) {
        pendingPuckVelocities.add(speed);
    }
}
