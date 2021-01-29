package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MathTool;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class Connect4GridDrawer {

    private final @NonNull Connect4Geometry geometry;

    private final Color backgroundColor = new Color(51, 25, 194, 255);

    public BufferedImage draw() {
        final var image = new BufferedImage(geometry.computeImageWidth(),geometry.computeImageHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        this.fillImageBackground(image);
        this.clearPositions(image);
        return image;
    }

    private void fillImageBackground(BufferedImage image) {
        final var graphics = setupGraphics2D(image.createGraphics());
        try {
            graphics.setBackground(backgroundColor);
            graphics.clearRect(0,0,image.getWidth(),image.getHeight());
        } finally {
            graphics.dispose();
        }
    }

    private Graphics2D setupGraphics2D(@NonNull Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        return graphics2D;
    }

    private void clearPositions(BufferedImage image) {
        final var graphics = setupGraphics2D(image.createGraphics());
        graphics.setColor(new Color(0,0,0,0));
        graphics.setComposite(AlphaComposite.Src);

        try {
            geometry.streamPositions()
                    .map(geometry::computePositionOnImage)
                    .forEach(pt -> {
                        final int positionRadius = geometry.getPositionRadius();
                        final int px = MathTool.roundedToInt(pt.getX()-positionRadius);
                        final int py = MathTool.roundedToInt(pt.getY()-positionRadius);
                        graphics.fillOval(px,py,positionRadius*2,positionRadius*2);
                    });
        } finally {
            graphics.dispose();
        }

    }



}
