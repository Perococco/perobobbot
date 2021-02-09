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
        renderer.drawImage(image,x,y,
                           tileGeometry.getX(),tileGeometry.getY(),
                           tileGeometry.getWidth(), tileGeometry.getHeight());
    }

    @Override
    public void render(@NonNull Renderer renderer, double x, double y, double width, double height) {
        renderer.drawImage(image,x,y,width,height,
                           tileGeometry.getX(),tileGeometry.getY(),
                           tileGeometry.getWidth(), tileGeometry.getHeight());
    }
}
