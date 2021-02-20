package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.tile.Tile;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class WallTiles {

    public static @NonNull WallTiles with(@NonNull Tile...tiles) {
        return new WallTiles(List.of(tiles),false);
    }

    public static @NonNull WallTiles placeHolder(@NonNull Tile placeHolderTile) {
        return new WallTiles(List.of(placeHolderTile),true);
    }

    private final @NonNull List<Tile> tiles;

    @Getter
    private final boolean placeHolder;

    public @NonNull Stream<Tile> tileStream() {
        return tiles.stream();
    }
}
