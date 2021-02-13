package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.entity.Direction;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.List;
import java.util.stream.Stream;

import static perobobbot.dungeon.game.DungeonTile.*;

public class WallTileOnFloor extends WallTileBase {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap,
                                                     @NonNull Position position,
                                                     @NonNull List<MissingPosition> missingPositions) {
        return new WallTileOnFloor(dungeonMap, position, missingPositions).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonMap dungeonMap;
    @Getter
    private final @NonNull Position position;

    public WallTileOnFloor(@NonNull DungeonMap dungeonMap, @NonNull Position position, @NonNull List<MissingPosition> missingPositions) {
        super(UI_HEART_FULL, FLASK_BIG_BLUE, missingPositions);
        this.dungeonMap = dungeonMap;
        this.position = position;
    }


    private @NonNull Stream<Tile> getWallTiles() {
        final var cell = dungeonMap.getCellAt(position);
        final var flag = cell.getFlag();
        return switch (flag) {
            case 0b110_101_100 -> Stream.of(WALL_TOP_MID);
            case 0b111_000_010 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b011_101_001 -> Stream.empty();
            case 0b011_001_011 -> Stream.empty();
            case 0b111_001_011 -> Stream.empty();
            case 0b011_101_011 -> Stream.empty();
            case 0b001_101_011 -> Stream.empty();
            case 0b111_101_011 -> Stream.empty();
            case 0b010_001_011 -> Stream.empty();
            case 0b000_101_111 -> Stream.empty();
            case 0b001_101_111 -> Stream.empty();
            case 0b011_101_111 -> Stream.empty();
            case 0b100_101_111 -> Stream.empty();
            case 0b101_101_111 -> Stream.empty();
            case 0b110_101_111 -> Stream.empty();
            case 0b111_101_111 -> Stream.empty();
            case 0b011_001_111 -> Stream.empty();
            case 0b111_001_111 -> Stream.empty();
            case 0b010_101_111 -> Stream.empty();
            case 0b001_001_011 -> Stream.empty();
            case 0b000_001_111 -> Stream.empty();
            case 0b000_001_011 -> Stream.empty();
            case 0b001_001_111 -> Stream.empty();
            case 0b110_001_011 -> Stream.empty();
            case 0b000_101_011 -> Stream.empty();
            case 0b110_101_001 -> Stream.of(WALL_INNER_CORNER_T_TOP_RIGHT);
            case 0b100_101_011 -> Stream.empty();
            case 0b001_101_100 -> Stream.of(WALL_TOP_MID);
            case 0b111_000_011 -> Stream.of(WALL_SIDE_FRONT_LEFT);

            case 0b110_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT));

            case 0b010_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder());

            case 0b100_101_101 -> Stream.of(WALL_CORNER_TOP_RIGHT);
            case 0b001_101_101 -> Stream.of(WALL_CORNER_TOP_RIGHT);

            case 0b011_101_100 -> Stream.of(WALL_TOP_MID);
            case 0b001_101_110 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b110_100_100 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
                                             );


            case 0b001_101_001 -> forCase001_101_001();
            case 0b111_101_001 -> extraCases(cell,
//                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT)
            );
            case 0b011_001_001 -> extraCases(cell,
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT)
            );
            case 0b011_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder()
            );
            case 0b000_001_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             placeHolder()
            );
            case 0b111_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT)
            );
            case 0b010_101_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT)
            );
            case 0b110_100_000 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_100_000 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b110_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b000_100_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder()
            );
            case 0b110_101_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT)
            );
            case 0b110_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b011_100_000 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT)
            );
            case 0b011_001_100 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b011_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT)
            );
            case 0b111_000_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b100_100_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b011_000_010 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b010_000_010 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b100_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT),
                                             with(WALL_SIDE_FRONT_LEFT)
            );
            case 0b011_101_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT));
            case 0b010_101_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             placeHolder());
            case 0b011_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT));
            case 0b011_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT));
            case 0b110_000_110 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder());
            case 0b000_100_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b110_100_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b111_100_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b010_000_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b010_100_110 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b011_000_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b110_000_010 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b110_100_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT));
            case 0b110_101_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with());
            case 0b011_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder());
            case 0b011_100_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b010_100_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);

            case 0b000_101_110 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b011_001_110 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b111_101_010 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b011_001_010 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b111_101_110 -> Stream.of(WALL_SIDE_TOP_LEFT);
            case 0b110_101_110 -> Stream.of(WALL_SIDE_TOP_LEFT);

            case 0b110_100_011 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b111_100_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b010_000_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b110_100_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b010_000_011 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b111_000_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b000_100_111 -> Stream.of(WALL_SIDE_FRONT_LEFT);

            case 0b100_101_100 -> Stream.of(WALL_TOP_LEFT);
            case 0b111_101_100 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             with(WALL_INNER_CORNER_T_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_TOP_MID));
            case 0b111_101_101 -> Stream.of(WALL_CORNER_TOP_RIGHT);
            case 0b101_101_101 -> Stream.of(WALL_CORNER_TOP_RIGHT);

            case 0b011_101_110 -> Stream.of(WALL_SIDE_TOP_LEFT);

            case 0b101_101_100 -> Stream.of(WALL_TOP_LEFT);
            case 0b001_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             with(WALL_INNER_CORNER_T_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_MID));
            case 0b110_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT));
            case 0b000_001_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT));
            case 0b010_101_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with());
            case 0b100_101_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             placeHolder());
            case 0b110_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT));
            case 0b111_100_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT));
            case 0b101_101_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             placeHolder());
            case 0b111_001_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT));
            case 0b000_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT));
            case 0b011_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_LEFT)
            );
            case 0b000_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT)
            );
            case 0b111_001_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_TOP_LEFT)
            );
            case 0b110_001_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with()
            );
            case 0b111_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT)
            );
            case 0b010_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_101_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b010_001_000 -> extraCases(cell,
                                             with(WALL_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b000_101_100 -> extraCases(cell,
                                             with(WALL_TOP_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder()
            );
            case 0b110_001_000 -> extraCases(cell,
                                             with(WALL_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b000_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_001_101 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b110_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_T_TOP_RIGHT),
                                             with(WALL_TOP_MID)
            );
            case 0b100_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_TOP_MID)
            );
            case 0b100_100_010 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder()
            );
            case 0b000_101_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT)
            );
            case 0b011_001_000 -> extraCases(cell,
                                             with(WALL_TOP_LEFT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT)
            );

            case 0b101_101_001 -> extraCases(cell,
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             placeHolder(),
                                             with(WALL_TOP_RIGHT)
            );
            case 0b111_001_000 -> extraCases(cell,
                                             with(WALL_TOP_LEFT),
                                             with(WALL_TOP_LEFT),
                                             with(WALL_TOP_LEFT),
                                             with(WALL_TOP_LEFT)
            );
            case 0b111_101_000 -> extraCases(cell,
                                             with(WALL_TOP_MID),
                                             with(WALL_INNER_CORNER_T_TOP_RIGHT),
                                             with(WALL_INNER_CORNER_T_TOP_RIGHT),
                                             with(WALL_TOP_LEFT));
            case 0b111_100_100 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b010_001_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with()
            );


            case 0b100_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with()
            );

            case 0b010_100_000 -> extraCases(cell,
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             placeHolder()
            );


            default -> getDefault();
        };
    }

    private Stream<Tile> forCase001_101_001() {
        final var type = dungeonMap.getCellTypeAt(Direction.SOUTH.moveBy(position, 2));
        if (isFloor(type)) {
            return Stream.of(DungeonTile.WALL_TOP_RIGHT);
        }
        return Stream.of(WALL_INNER_CORNER_T_TOP_RIGHT);
    }

    private boolean isFloor(@NonNull CellType cellType) {
        return cellType.isFloor() || cellType == CellType.DOOR;
    }
}
