package perobobbot.rendering;

import lombok.NonNull;
import perobobbot.physics.Entity2DBase;

import java.awt.image.BufferedImage;

public class Sprite extends Entity2DBase implements Renderable {

    private final @NonNull BufferedImage image;

    private final int width;

    private final int height;

    public Sprite(@NonNull BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public Sprite(@NonNull String name, @NonNull BufferedImage image) {
        super(name);
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        renderer.withPrivateTransform(r -> {
                    final var xOffset = width*0.5;
                    final var yOffset = height*0.5;
                    r.translate(getPosition());
                    r.rotate(getAngle());
                    r.translate(-xOffset,-yOffset);
                    r.drawImage(image,0,0);
                }
        );

    }
}
