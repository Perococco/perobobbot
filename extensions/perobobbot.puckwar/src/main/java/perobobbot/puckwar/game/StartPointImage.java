package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StartPointImage {

    public static BufferedImage create(int size) {
        return new StartPointImage(size,size/2,size/2-2).create();
    }



    private static final Color TARGET_COLOR = new Color(255, 0, 0, 255);

    private final int size;
    private final int areaSize;
    private final int radius;
    private double[] angles;
    private BufferedImage image;
    private Graphics2D graphics2D;


    private @NonNull BufferedImage create() {
        this.createImage();
        this.clearImage();
        this.setupTransformation();
        this.setupRenderingQuality();
        this.computeAngles();
        this.drawLines();
        this.drawArc();
        this.drawTexts();
        return image;
    }

    private void computeAngles() {
        final int nbAngles = 4;
        this.angles = IntStream.range(0,nbAngles).mapToDouble(i -> Math.PI*i/(2*(nbAngles-1))).toArray();
    }

    private void drawTexts() {

    }

    private void drawLines() {
        for (double angle : angles) {
            final Graphics2D g2 = (Graphics2D) graphics2D.create();
            try {
                g2.rotate(-angle, 1, areaSize - 1);

                final var x = 1;
                final var y = areaSize - 1;

                g2.setColor(Color.WHITE);
                g2.drawLine(x, y, x+radius, y);

                final var angleInDegree =  Math.toDegrees(angle);

                final TextLayout tx = new TextLayout(String.format("%.0f°",angleInDegree),g2.getFont(),g2.getFontRenderContext());
                final Rectangle2D bounds = tx.getBounds();
                final var yOffset = angleInDegree>10? y+bounds.getHeight()+tx.getDescent() : y-tx.getDescent();

                tx.draw(g2,(float)(x  +radius*0.8),(float)(yOffset));

            } finally {
                g2.dispose();
            }
        }
    }

    private void drawArc() {
        graphics2D.translate(areaSize -0.5, areaSize -0.5);
        graphics2D.drawArc(-2*radius-1, -radius-1,2*radius+1, 2*radius+1, 0, 90);
    }


    private void createImage() {
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
        this.graphics2D = this.image.createGraphics();
    }

    private void clearImage() {
        graphics2D.setBackground(Renderer.TRANSPARENT);
        graphics2D.clearRect(0, 0, size, size);
    }

    private void setupTransformation() {
        final var scale = areaSize / (double) size;
        this.graphics2D.scale(1. / scale, 1. / scale);
    }

    private void setupRenderingQuality() {
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
