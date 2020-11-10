package perobobbot.puckwar;

import lombok.NonNull;
import perobobbot.common.math.MVector2D;
import perobobbot.common.math.Rectangle;
import perobobbot.common.math.Vector2D;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;

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
        final int width = iteration.getWidth();
        final int height = iteration.getHeight();
        final double dt = iteration.getDeltaTime();

        this.updatePuckPositions(dt);
        this.addPendingPucks();
        this.cleanUpPucks(width, height);

        final Graphics2D g2 = iteration.createGraphics2D();
        setupRenderingHints(g2);
        try {
            pucks.forEach(p -> p.draw(g2));
        } finally {
            g2.dispose();
        }
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

    private void cleanUpPucks(int width, int height) {
        pucks.removeIf(isPuckOutsideOverlay(width, height));
    }

    private Predicate<Puck> isPuckOutsideOverlay(int width, int height) {
        final Rectangle overlay = new Rectangle(-puckSize, -puckSize, width + 2*puckSize, height + 2*puckSize);
        return puck -> !overlay.contains(puck.getPosition());
    }

    private void setupRenderingHints(@NonNull Graphics2D graphics2D) {
        if (nice) {
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        }
    }

    public void throwPuck(@NonNull Vector2D speed) {
        pendingPuckVelocities.add(speed);
    }
}
