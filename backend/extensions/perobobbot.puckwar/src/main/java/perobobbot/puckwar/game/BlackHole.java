package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.math.Vector2D;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BlackHole implements Renderable {

    public static @NonNull BlackHole create(@NonNull Vector2D position) {
        return new BlackHole(Holder.BLACK_HOLE_IMAGE,position);
    }

    private final BufferedImage image;

    private final @NonNull Vector2D position;

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        if (image != null) {
            renderer.translate(position.x(), position.y());
            renderer.drawImage(image, 0, 0);
            renderer.translate(-position.x(), -position.y());
        }
    }



    public static int getImageSize() {
        final BufferedImage image = Holder.BLACK_HOLE_IMAGE;
        return Math.max(image.getHeight(), image.getWidth());
    }

    public static class Holder {

        private static final BufferedImage BLACK_HOLE_IMAGE;

        static {
            try {
                BLACK_HOLE_IMAGE = ImageIO.read(BlackHole.class.getResourceAsStream("blackhole.png"));
            } catch (IOException e) {
                LOG.error("Could not load resource file",e);
                throw new UncheckedIOException(e);
            }
        }

    }
}
