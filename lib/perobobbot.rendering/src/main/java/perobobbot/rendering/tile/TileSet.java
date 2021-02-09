package perobobbot.rendering.tile;

import lombok.NonNull;

public interface TileSet {

    @NonNull Tile extractTile(@NonNull TileGeometry tileGeometry);
}
