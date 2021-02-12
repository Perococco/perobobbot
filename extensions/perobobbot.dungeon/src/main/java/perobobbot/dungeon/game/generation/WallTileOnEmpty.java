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
            case 0b000_000_001 -> Stream.empty();
            case 0b001_001_001 -> Stream.empty();
            case 0b000_001_001 -> Stream.empty();
            case 0b000_000_000 -> Stream.empty();
            case 0b111_000_000 -> Stream.empty();
            case 0b111_001_001 -> Stream.empty();
            case 0b110_000_000 -> Stream.empty();
            case 0b100_000_000 -> Stream.empty();
            case 0b100_100_000 -> Stream.empty();
            case 0b100_100_100 -> Stream.empty();
            case 0b111_100_100 -> Stream.empty();
            case 0b000_100_100 -> Stream.empty();
            case 0b000_000_100 -> Stream.empty();
            case 0b000_000_110 -> Stream.empty();
            case 0b111_100_110 -> Stream.empty();
            case 0b111_000_100 -> Stream.empty();
            case 0b111_001_000 -> Stream.empty();
            case 0b110_100_100 -> Stream.empty();
            case 0b110_000_110 -> Stream.empty();
            case 0b100_000_100 -> Stream.empty();
            case 0b111_101_101 -> Stream.empty();
            case 0b101_101_101 -> Stream.empty();

            case 0b111_000_001 -> Stream.empty();
            case 0b111_000_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);

            case 0b111_100_111 -> forCase111_100_111(cell);
            case 0b111_000_111 -> forCase111_000_111(cell);
            case 0b100_000_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b100_100_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b001_001_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b000_100_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b000_001_111 -> forCase000_001_111(cell);
            case 0b110_000_111 -> Stream.of(DungeonTile.WALL_TOP_MID);


            case 0b001_001_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
            case 0b000_000_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);

            case 0b000_000_111 -> forCase000_000_111(cell);

            case 0b001_001_000 -> Stream.empty();
            case 0b001_000_000 -> Stream.empty();
            case 0b011_001_001 -> Stream.empty();
            case 0b011_000_000 -> Stream.empty();
            case 0b100_100_110 -> Stream.empty();
            case 0b110_100_000 -> Stream.empty();
            case 0b111_100_000 -> Stream.empty();

            case 0b100_100_101 -> Stream.empty();
            case 0b100_101_101 -> Stream.empty();
            case 0b101_101_111 -> Stream.of(DungeonTile.WALL_INNER_CORNER_T_TOP_RIGTH);

            case 0b111_001_111 -> Stream.of(DungeonTile.WALL_TOP_MID);


            default -> Stream.of(DungeonTile.CRATE);
        };
    }

    private Stream<Tile> forCase111_000_111(DungeonCell cell) {
        final var south2Position = Direction.SOUTH.moveBy(position, 2);
        final var south2EastPosition = Direction.EAST.moveByOne(south2Position);

        final var cellTypeSS = dungeonMap.getCellTypeAt(south2Position);
        final var cellTypeSSE = dungeonMap.getCellTypeAt(south2EastPosition);
        if (isFloor(cellTypeSS)) {
            if (isFloor(cellTypeSSE)) {
                return Stream.of(DungeonTile.WALL_TOP_MID);
            } else {
                return Stream.of(DungeonTile.WALL_INNER_CORNER_T_TOP_RIGTH);
            }
        }
        return Stream.of(DungeonTile.WALL_TOP_MID);
    }

    private Stream<Tile> forCase111_100_111(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_TOP_MID);
        } else {
            return Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
        }
    }

    private Stream<Tile> forCase000_000_111(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_TOP_RIGHT);
        }
        return Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
    }

    private Stream<Tile> forCase000_001_111(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_TOP_MID);
        }
        return Stream.empty();
    }

    private boolean isFloor(@NonNull CellType cellType) {
        return cellType.isFloor() || cellType == CellType.DOOR;
    }

    public boolean isTwoTileSouthAFloor() {
        final var type = dungeonMap.getCellTypeAt(Direction.SOUTH.moveBy(position, 2));
        return isFloor(type);
    }

}
