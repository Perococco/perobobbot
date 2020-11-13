package perobobbot.puckwar.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.math.ImmutableVector2D;
import perobobbot.math.ReadOnlyVector2D;
import perobobbot.rendering.Renderable;
import perobobbot.rendering.Renderer;

import java.awt.image.BufferedImage;

public class Target implements Renderable {

    @Getter
    private final @NonNull ImmutableVector2D position;

    @Getter
    private final @NonNull BufferedImage image;

    private final double offsetX;
    private final double offsetY;

    public Target(@NonNull ImmutableVector2D position, int size) {
        this.position = position;
        this.offsetX = position.x() - size * 0.5;
        this.offsetY = position.y() - size * 0.5;
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
        TargetDrawer.draw(image.createGraphics(), size);
    }

    public boolean isPointInside(@NonNull ReadOnlyVector2D position) {
        return this.position.normOfDifference(position) < image.getWidth()*0.5;
    }

    @NonNull
    @Override
    public void drawWith(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> r.translate(offsetX, offsetY)
                                          .drawImage(image, 0, 0)
        );
    }
}
