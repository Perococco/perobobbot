package perobobbot.puckwar;

import lombok.NonNull;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class PuckWarOverlay implements OverlayClient {

    private static final Puck[] NO_PUCK = new Puck[0];

    private final int nbPucks;
    private final int puckSize;

    private boolean nice = false;

    private Puck[] pucks;

    public PuckWarOverlay(int nbPucks,  int puckSize) {
        this.nbPucks = nbPucks;
        this.puckSize = puckSize;
    }

    @Override
    public void initialize(@NonNull Overlay overlay) {
        final var factory = new PuckFactory(overlay.getWidth(), overlay.getHeight(),puckSize);
        this.pucks = IntStream.range(0, nbPucks)
                              .mapToObj(factory::randomPuck)
                              .toArray(Puck[]::new);
    }

    @Override
    public void dispose(@NonNull Overlay overlay) {
        pucks = NO_PUCK;
    }

    public void setNice(boolean nice) {
        this.nice = nice;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final int width = iteration.getWidth();
        final int height = iteration.getHeight();
        final float dt = (float)iteration.getDeltaTime();
        final Graphics2D g2 = iteration.createGraphics2D();
        if (nice) {
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        }
        try {
            Arrays.stream(pucks).forEach(p -> p.update(width,height,dt).draw(g2));
        } finally {
            g2.dispose();
        }
    }
}
