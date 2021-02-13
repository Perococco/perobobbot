package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.entity.Direction;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.List;
import java.util.stream.Stream;

public class WallTileOnEmpty extends WallTileBase {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap, @NonNull Position position, @NonNull List<MissingPosition> missingPositions) {
        return new WallTileOnEmpty(dungeonMap, position, missingPositions).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonMap dungeonMap;
    @Getter
    private final @NonNull Position position;

    public WallTileOnEmpty(@NonNull DungeonMap dungeonMap, @NonNull Position position, @NonNull List<MissingPosition> missingPositions) {
        super(DungeonTile.CRATE, DungeonTile.FLASK_BIG_GREEN, missingPositions);
        this.dungeonMap = dungeonMap;
        this.position = position;
    }


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
            case 0b001_001_000 -> Stream.empty();
            case 0b001_000_000 -> Stream.empty();
            case 0b011_001_001 -> Stream.empty();
            case 0b011_000_000 -> Stream.empty();
            case 0b100_100_110 -> Stream.empty();
            case 0b110_100_000 -> Stream.empty();
            case 0b111_100_000 -> Stream.empty();
            case 0b100_100_101 -> Stream.empty();
            case 0b100_101_101 -> Stream.empty();
            case 0b111_000_001 -> Stream.empty();
            case 0b111_000_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
            case 0b100_000_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b000_100_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b110_000_111 -> Stream.of(DungeonTile.WALL_TOP_MID);
            case 0b001_001_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);
            case 0b000_000_011 -> Stream.of(DungeonTile.WALL_SIDE_TOP_LEFT);

            case 0b110_100_110 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b011_001_000 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b110_000_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_001_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_100_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );


            case 0b111_100_111 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_TOP_MID),
                                             with(DungeonTile.WALL_TOP_MID)
            );

            case 0b011_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b110_001_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_000_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b001_001_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_101_000 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_000 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_000_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b000_100_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b000_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b011_001_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b101_100_110 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_000_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b100_101_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b100_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b111_001_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b110_100_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_000_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_001_110 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_101_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_000_110 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b100_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b000_001_111 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));

            case 0b100_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b111_101_111 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b101_001_111 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b001_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b101_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b000_001_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_000_110 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_100_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_101_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b000_101_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_100_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b000_000_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b001_001_111 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));

            case 0b000_000_111 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_INNER_CORNER_T_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b101_101_111 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b110_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID));

            case 0b111_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );

            case 0b101_000_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b110_000_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b011_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_001 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_100_000 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_101_100 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_101_101 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );

            case 0b111_001_111 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_MID)
            );

            case 0b101_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID)
            );
            case 0b110_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );

            case 0b101_001_001,
                    0b111_101_100,
                    0b100_000_110,
                    0b001_001_100,
                    0b001_100_100,
                    0b101_101_000,
                    0b101_000_000,
                    0b100_000_001,
                    0b101_101_001,
                    0b000_100_110 -> extraCases(cell,
                                                with(),
                                                placeHolder(),
                                                placeHolder(),
                                                placeHolder()
            );
            default -> getDefault();
        };
    }


    private boolean isFloor(@NonNull CellType cellType) {
        return cellType.isFloor() || cellType == CellType.DOOR;
    }

    public boolean isTwoTileSouthAFloor() {
        final var type = dungeonMap.getCellTypeAt(Direction.SOUTH.moveBy(position, 2));
        return isFloor(type);
    }

}
