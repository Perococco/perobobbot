package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.math.Vector2D;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;

import java.awt.image.BufferedImage;

public class Target implements Renderable {

    @Getter
    private final @NonNull Vector2D position;

    @Getter
    private final @NonNull BufferedImage image;

    private final double offsetX;
    private final double offsetY;

    public Target(@NonNull Vector2D position, int size) {
        this.position = position;
        this.offsetX = position.x() - size * 0.5;
        this.offsetY = position.y() - size * 0.5;
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
        TargetDrawer.draw(image.createGraphics(), size);
    }

    @NonNull
    @Override
    public void drawWith(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> r.translate(offsetX, offsetY)
                                          .drawImage(image, 0, 0)
        );
    }
}
