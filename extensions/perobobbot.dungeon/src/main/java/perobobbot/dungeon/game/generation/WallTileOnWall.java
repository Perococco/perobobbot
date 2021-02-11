package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.entity.Direction;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class WallTileOnWall {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap, @NonNull Position position) {
        return new WallTileOnWall(dungeonMap, position).getWallTiles();
    }

    private final @NonNull DungeonMap dungeonMap;
    private final @NonNull Position position;

    private @NonNull Stream<Tile> getWallTiles() {
        final var cell = dungeonMap.getCellAt(position);
        final var flag = cell.getFlag();
        return switch (flag) {
            case 0b111_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b101_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b001_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b111_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b001_001_001 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b101_101_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b111_101_101 -> forCase111_101_101(cell);
            case 0b100_100_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);

            case 0b100_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b000_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_000_111 -> Stream.of(DungeonTile.WALL_MID);
            default -> Stream.of(DungeonTile.COIN_ANIM_0);
        };
    }

    private Stream<Tile> forCase111_101_101(@NonNull DungeonCell cell) {
        final var s = dungeonMap.getCellTypeAt(Direction.SOUTH.moveBy(position,2));
        if (s.isFloor() || s == CellType.DOOR) {
            return Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT, DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        } else {
            return Stream.of(DungeonTile.WALL_CORNER_RIGHT);
        }
    }
}
