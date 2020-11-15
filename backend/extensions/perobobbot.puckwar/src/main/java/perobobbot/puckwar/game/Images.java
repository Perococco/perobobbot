package perobobbot.puckwar.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

@RequiredArgsConstructor
public enum Images {

    BLACK_HOLE("/perobobbot/puckwar/game/blackhole.png"),
    ;

    private final @NonNull String resourceName;
    private Reference<BufferedImage> image = null;

    public @NonNull BufferedImage getImage() {
        final Reference<BufferedImage> ref = this.image;
        BufferedImage img = ref == null ? null : ref.get();
        if (img == null) {
            img = loadImage();
            image = new SoftReference<>(img);
        }
        return img;
    }

    private @NonNull BufferedImage loadImage() {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(resourceName));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
