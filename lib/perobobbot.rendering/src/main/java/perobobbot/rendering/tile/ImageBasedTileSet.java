package perobobbot.rendering.tile;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class ImageBasedTileSet implements TileSet {

    private final @NonNull BufferedImage image;

    @Override
    public @NonNull Tile extractTile(@NonNull TileGeometry tileGeometry) {
        return new ImageBasedTile(image, tileGeometry);
    }
}
