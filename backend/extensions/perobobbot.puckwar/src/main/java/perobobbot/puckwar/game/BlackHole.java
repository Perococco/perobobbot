package perobobbot.puckwar.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.math.ImmutableVector2D;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BlackHole implements Renderable {

    public static @NonNull BlackHole create(@NonNull ImmutableVector2D position) {
        return new BlackHole(Holder.BLACK_HOLE_IMAGE, position);
    }

    private static final double ANGULAR_SPEED = -Math.PI * 2 / 30.;

    private final BufferedImage image;

    @Getter
    private final @NonNull ImmutableVector2D position;

    private double angle = 0;

    public void update(double dt) {
        this.angle += ANGULAR_SPEED * dt;
    }

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        if (image != null) {
            renderer.withPrivateContext(r -> {
                final var xOffset = image.getWidth()*0.5;
                final var yOffset = image.getWidth()*0.5;
                        r.translate(position.x(),position.y());
                        r.rotate(angle);
                        r.translate(-xOffset,-yOffset);
                        r.drawImage(image,0,0);
                    }
            );
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
                LOG.error("Could not load resource file", e);
                throw new UncheckedIOException(e);
            }
        }

    }
}
