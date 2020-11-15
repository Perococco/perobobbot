package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.IntConsumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetImage {

    public static @NonNull BufferedImage create(int size) {
        return new TargetImage(size).create();
    }

    public static final int DRAWING_AREA_SIZE = 1000;

    private static final Color TARGET_COLOR = new Color(255,0, 0, 255);

    private final int size;

    private BufferedImage image;
    private Graphics2D graphics2D;

    public @NonNull BufferedImage create() {
        this.createImage();
        this.clearImage();
        this.setupTransformation();
        this.setupRenderingQuality();
        this.drawCircles();
        this.drawReticule();
        return image;
    }

    private void createImage() {
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
        this.graphics2D = this.image.createGraphics();
    }

    private void clearImage() {
        graphics2D.setBackground(Renderer.TRANSPARENT);
        graphics2D.clearRect(0,0,size,size);
    }

    private void setupTransformation() {
        final var scale = 2.*DRAWING_AREA_SIZE/(double)size;
        this.graphics2D.scale(1./scale,1./scale);
        this.graphics2D.translate(DRAWING_AREA_SIZE,DRAWING_AREA_SIZE);
    }

    private void setupRenderingQuality() {
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawCircles() {
        graphics2D.setComposite(AlphaComposite.Src);
        final IntConsumer fillCircle = r -> {
            graphics2D.setColor(TARGET_COLOR);
            graphics2D.fillOval(-r,-r,r*2,r*2);
        };
        final IntConsumer clearCircle = r -> {
            graphics2D.setColor(Renderer.TRANSPARENT);
            graphics2D.fillOval(-r,-r,r*2,r*2);
        };
        for (int i = 5; i > 0; i--) {
            final int radius = DRAWING_AREA_SIZE/5*i;
            if (i%2==1) {
                fillCircle.accept(radius);
            } else {
                clearCircle.accept(radius);
            }
        }
    }

    private void drawReticule() {
        graphics2D.setComposite(AlphaComposite.Src);
        final int l = DRAWING_AREA_SIZE/10;
        graphics2D.setStroke(new BasicStroke(8));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawLine(l,0,-l,0);
        graphics2D.drawLine(0,l,0,-l);
    }



}
