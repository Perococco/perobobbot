package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.experimental.Delegate;
import perobobbot.rendering.tile.ImageBasedTileSet;
import perobobbot.rendering.tile.Tile;
import perobobbot.rendering.tile.TileSet;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.UncheckedIOException;

public enum DungeonTileSet implements TileSet {
    INSTANCE,
    ;

    @Delegate
    private final TileSet delegate;

    public static final String  DUNGEON_TILESET_0x72_v2 ="0x72_DungeonTilesetII_v1.3.png";


    DungeonTileSet() {
        try {
            final var image = ImageIO.read(DungeonTileSet.class.getResource(DUNGEON_TILESET_0x72_v2));
            this.delegate = new ImageBasedTileSet(image);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
