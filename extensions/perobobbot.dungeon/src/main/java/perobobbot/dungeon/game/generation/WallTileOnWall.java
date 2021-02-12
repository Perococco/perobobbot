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
            case 0b100_000_000 -> forCase100_000_000(cell);

            case 0b000_000_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b000_000_100 -> forCase000_000_100(cell);
            case 0b100_100_100 -> forCase100_100_100(cell);
            case 0b100_100_000 -> forCase100_100_000(cell);

            case 0b000_100_101 -> Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
            case 0b100_101_111 -> Stream.of(DungeonTile.WALL_RIGHT);

            case 0b000_100_100 -> forCase000_100_100(cell);

            case 0b110_000_000 -> forCase110_000_000(cell);
            case 0b011_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);

            case 0b111_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b101_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b000_001_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);

            case 0b001_001_000 -> forCase001_001_000(cell);
            case 0b000_001_001 -> forCase000_001_000(cell);
            case 0b000_100_111 -> Stream.of(DungeonTile.WALL_MID);

            case 0b110_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b001_000_011 -> Stream.of(DungeonTile.WALL_MID);

            case 0b111_001_001 -> forCase111_001_001(cell);
            case 0b100_100_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b011_001_001 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);

            case 0b001_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b111_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b000_000_001 -> forCase000_000_001(cell);
            case 0b001_001_001 -> forCase001_001_001(cell);
            case 0b101_101_101 -> forCase101_101_101(cell);
            case 0b111_101_101 -> forCase111_101_101(cell);
            case 0b011_001_011 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b001_000_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b000_101_101 -> Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
            case 0b111_001_000 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b111_100_000 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_LEFT);
            case 0b000_001_101 -> Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
            case 0b001_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b100_100_101 -> forCase100_100_101(cell);

            case 0b111_000_000 -> forCase111_000_000(cell);
            case 0b111_100_100 -> forCase111_100_100(cell);
            case 0b000_000_011 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_LEFT);
            case 0b100_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b000_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_000_111 -> Stream.of(DungeonTile.WALL_MID);

            case 0b001_001_011 -> Stream.of(DungeonTile.WALL_RIGHT);

            case 0b100_101_001 -> forCase100_101_001(cell);

            case 0b101_101_000 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b001_001_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b001_101_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b101_101_100 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b101_100_100 -> Stream.of(DungeonTile.WALL_SIDE_FRONT_LEFT);
            case 0b110_100_100 -> forCase110_100_100(cell);

            case 0b110_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b100_000_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);

            case 0b101_000_000 -> Stream.of(DungeonTile.WALL_SIDE_FRONT_LEFT);

            case 0b111_101_001 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b101_001_001 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b110_100_000 -> Stream.of(DungeonTile.WALL_RIGHT);

            case 0b000_000_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b111_101_100 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);

            case 0b101_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);

            case 0b011_000_000 -> forCase011_000_000(cell);
            case 0b001_000_000 -> Stream.of(DungeonTile.WALL_SIDE_FRONT_LEFT);
            default -> Stream.of(DungeonTile.COIN_ANIM_0);
        };
    }

    private Stream<Tile> forCase110_000_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_RIGHT,DungeonTile.WALL_TOP_MID);
        }
        return Stream.of(DungeonTile.WALL_RIGHT);
    }

    private Stream<Tile> forCase111_100_100(DungeonCell cell) {
        final var south2Position = Direction.SOUTH.moveBy(position, 2);
        final var south2EastPosition = Direction.EAST.moveByOne(south2Position);

        final var cellTypeSS = dungeonMap.getCellTypeAt(south2Position);
        final var cellTypeSSE = dungeonMap.getCellTypeAt(south2EastPosition);

        if (isFloor(cellTypeSS)) {
            return Stream.of(DungeonTile.WALL_MID, DungeonTile.WALL_TOP_MID);
        } else if (isFloor(cellTypeSSE)) {
            return Stream.of(DungeonTile.WALL_MID,DungeonTile.WALL_SIDE_TOP_LEFT);
        } else {
            return Stream.of(DungeonTile.WALL_MID);
        }
    }

    private Stream<Tile> forCase100_101_001(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        }
        return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
    }

    private Stream<Tile> forCase100_100_101(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        }
        return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
    }

    private Stream<Tile> forCase100_000_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_TOP_MID);
        }
        return Stream.empty();
    }

    private Stream<Tile> forCase110_100_100(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_RIGHT, DungeonTile.WALL_TOP_MID);
        }
        return Stream.of(DungeonTile.WALL_RIGHT);
    }

    private Stream<Tile> forCase100_100_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_TOP_RIGHT);
        }
        return Stream.empty();
    }

    private Stream<Tile> forCase001_001_001(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        }
        return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
    }

    private Stream<Tile> forCase101_101_101(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        }

        return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);

    }

    private Stream<Tile> forCase011_000_000(DungeonCell cell) {
        final var posSSE = Direction.EAST.moveByOne(Direction.SOUTH.moveBy(position, 2));
        final var typeSSE = dungeonMap.getCellTypeAt(posSSE);
        if (isFloor(typeSSE)) {
            return Stream.of(DungeonTile.WALL_MID, DungeonTile.WALL_SIDE_TOP_LEFT);
        }
        return Stream.of(DungeonTile.WALL_MID);

    }

    private Stream<Tile> forCase000_100_100(DungeonCell cell) {
        final var south2Position = Direction.SOUTH.moveBy(position, 2);
        final var south2EastPosition = Direction.EAST.moveByOne(south2Position);

        final var cellTypeSS = dungeonMap.getCellTypeAt(south2Position);
        final var cellTypeSSE = dungeonMap.getCellTypeAt(south2EastPosition);
        if (isFloor(cellTypeSSE)) {
            if (cellTypeSS == CellType.WALL) {
                return Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
            } else {
                return Stream.of(DungeonTile.WALL_TOP_MID);
            }
        }
        return Stream.empty();
    }

    private Stream<Tile> forCase100_100_100(DungeonCell cell) {
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
        return Stream.empty();

    }

    private Stream<Tile> forCase000_000_100(DungeonCell cell) {
        final var south2Position = Direction.SOUTH.moveBy(position, 2);
        final var south2EastPosition = Direction.EAST.moveByOne(south2Position);

        final var cellTypeSS = dungeonMap.getCellTypeAt(south2Position);
        final var cellTypeSSE = dungeonMap.getCellTypeAt(south2EastPosition);
        if (isFloor(cellTypeSSE)) {
            if (cellTypeSS == CellType.WALL) {
                return Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
            } else {
                return Stream.of(DungeonTile.WALL_TOP_MID);
            }
        }
        return Stream.empty();
    }

    private Stream<Tile> forCase000_001_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        }
        return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
    }

    private Stream<Tile> forCase111_001_001(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        } else {
            return Stream.of(DungeonTile.WALL_CORNER_RIGHT);
        }
    }

    private Stream<Tile> forCase001_001_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        } else {
            return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
        }
    }


    private Stream<Tile> forCase000_000_001(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        } else {
            return Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
        }
    }

    private Stream<Tile> forCase111_000_000(DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_MID, DungeonTile.WALL_TOP_MID);
        }
        return Stream.of(DungeonTile.WALL_MID);
    }

    private Stream<Tile> forCase111_101_101(@NonNull DungeonCell cell) {
        if (isTwoTileSouthAFloor()) {
            return Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT, DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
        } else {
            return Stream.of(DungeonTile.WALL_CORNER_RIGHT);
        }
    }

    private boolean isFloor(@NonNull CellType cellType) {
        return cellType.isFloor() || cellType == CellType.DOOR;
    }

    public boolean isTwoTileSouthAFloor() {
        final var type = dungeonMap.getCellTypeAt(Direction.SOUTH.moveBy(position, 2));
        return isFloor(type);
    }
}
