package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Position;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class WallTileOnEmpty {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap, @NonNull Position position) {
        return new WallTileOnEmpty(dungeonMap, position).getWallTiles();
    }

    private final @NonNull DungeonMap dungeonMap;
    private final @NonNull Position position;

    private @NonNull Stream<Tile> getWallTiles() {
        final var cell = dungeonMap.getCellAt(position);
        final var flag = cell.getFlag();
        return switch (flag) {
            case 0b000_000_000 -> Stream.empty();
            default -> Stream.of(DungeonTile.CRATE);
        };
    }
}
