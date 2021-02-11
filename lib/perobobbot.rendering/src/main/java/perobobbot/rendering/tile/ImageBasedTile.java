package perobobbot.rendering.tile;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class ImageBasedTile implements Tile {

    private final BufferedImage image;
    private final TileGeometry tileGeometry;

    public void render(@NonNull Renderer renderer, double x, double y) {
        final var width = tileGeometry.getWidth();
        final var height = tileGeometry.getHeight();
        renderer.drawImage(image,x,y,
                           tileGeometry.getX(),tileGeometry.getY(),
                           width,height);
    }

    @Override
    public void render(@NonNull Renderer renderer, double x, double y, double scale) {
        final var width = tileGeometry.getWidth();
        final var height = tileGeometry.getHeight();

        renderer.drawImage(image,x,y,width*scale,height*scale,
                           tileGeometry.getX(),tileGeometry.getY(),
                           width, height);
    }

    @Override
    public void render(@NonNull Renderer renderer) {
        render(renderer,0,0);
    }
}
