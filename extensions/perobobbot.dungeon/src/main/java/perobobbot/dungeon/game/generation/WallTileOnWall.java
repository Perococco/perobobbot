package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.entity.Direction;
import perobobbot.lang.Todo;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.List;
import java.util.stream.Stream;

import static perobobbot.dungeon.game.DungeonTile.*;

public class WallTileOnWall extends WallTileBase {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap,
                                                     @NonNull Position position,
                                                     @NonNull List<MissingPosition> missingPositions) {
        return new WallTileOnWall(dungeonMap, position, missingPositions).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonMap dungeonMap;
    @Getter
    private final @NonNull Position position;

    public WallTileOnWall(@NonNull DungeonMap dungeonMap, @NonNull Position position, @NonNull List<MissingPosition> missingPositions) {
        super(COIN_ANIM_0, FLASK_BIG_RED, missingPositions);
        this.dungeonMap = dungeonMap;
        this.position = position;
    }

    private @NonNull Stream<Tile> getWallTiles() {
        final var cell = dungeonMap.getCellAt(position);
        final var flag = cell.getFlag();
        return switch (flag) {

            case 0b100_000_100 -> Stream.empty();
            case 0b100_000_000 -> extraCases(cell,
                                             with(),
                                             placeHolder(),
                                             with(WALL_CORNER_TOP_RIGHT),
                                             with(WALL_CORNER_TOP_RIGHT));
            case 0b000_000_100, 0b000_100_100, 0b100_100_000, 0b100_100_100 -> extraCases(cell,
                                                                                          with(),
                                                                                          with(WALL_SIDE_TOP_LEFT),
                                                                                          with(WALL_CORNER_TOP_RIGHT),
                                                                                          with(WALL_TOP_LEFT));
            case 0b111_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             with(WALL_CORNER_RIGHT));
            case 0b101_101_001 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_111 -> extraCases(cell,
                                             with(WALL_MID),
                                             with(WALL_MID),
                                             placeHolder(),
                                             with(WALL_MID)
            );
            case 0b100_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_CORNER_FRONT_LEFT)
            );
            case 0b001_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT)
            );
            case 0b001_101_100 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_000_100 -> extraCases(cell,
                                             with(WALL_CORNER_FRONT_RIGHT),
                                             with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             with(WALL_TOP_LEFT)
            );
            case 0b101_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b100_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_000 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b100_001_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_RIGHT),
                                             placeHolder(),
                                             with(WALL_RIGHT)
            );
            case 0b110_000_000 -> extraCases(cell,
                                             with(WALL_RIGHT),
                                             with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                                             with(WALL_RIGHT, WALL_CORNER_TOP_RIGHT),
                                             with(WALL_RIGHT, WALL_TOP_LEFT));
            case 0b001_001_000 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_101_101 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_101_101 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b011_001_000 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_RIGHT),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_100_100 -> extraCases(cell,
                                             with(WALL_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT));
            case 0b100_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_100 -> extraCases(cell,
                                             with(WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_001_001 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b000_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b000_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_001_001 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_100_100 -> extraCases(cell,
                                             with(WALL_LEFT),
                                             with(WALL_LEFT, WALL_SIDE_TOP_LEFT),
                                             with(WALL_LEFT, WALL_TOP_LEFT),
                                             with(WALL_LEFT, WALL_TOP_LEFT)
            );
            case 0b100_101_001 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b110_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_100 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b110_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b111_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT)
            );

            case 0b110_100_100 -> extraCases(cell,
                                             with(WALL_RIGHT),
                                             with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                                             with(WALL_RIGHT, WALL_CORNER_TOP_RIGHT),
                                             with(WALL_TOP_LEFT)
            );
            case 0b011_000_000 -> extraCases(cell,
                                             with(WALL_MID),
                                             with(WALL_MID, WALL_SIDE_TOP_LEFT),
                                             with(WALL_MID, WALL_CORNER_TOP_RIGHT),
                                             with(WALL_MID, WALL_TOP_MID)
            );
            case 0b011_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_RIGHT)
            );
            case 0b011_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder());
            case 0b101_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b110_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder()
            );

            case 0b000_000_110, 0b100_000_110, 0b100_100_110 -> Stream.of(WALL_CORNER_RIGHT);
            case 0b011_001_001 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_001_000 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_101_001 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder());

            case 0b000_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b001_001_100 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_MID));
            case 0b001_100_100 -> extraCases(cell,
                                             with(WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_TOP_LEFT));
            case 0b110_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT));
            case 0b000_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder());
            case 0b011_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_LEFT),
                                             placeHolder(),
                                             placeHolder());

            case 0b111_101_111, 0b001_101_111, 0b000_001_111, 0b101_101_111 -> Stream.of(WALL_CORNER_FRONT_RIGHT);

            case 0b000_000_011 -> Stream.of(WALL_CORNER_FRONT_LEFT);

            case 0b100_101_111, 0b101_001_111, 0b110_100_000, 0b001_001_011, 0b011_001_011, 0b111_001_111, 0b001_001_111, 0b011_001_111 -> Stream.of(WALL_RIGHT);


            case 0b011_000_111, 0b001_000_111, 0b110_000_111, 0b111_000_111, 0b000_000_111, 0b111_100_111, 0b100_100_111, 0b001_000_011, 0b110_100_111, 0b000_100_111 -> Stream.of(WALL_MID);


            case 0b001_000_101, 0b100_000_001 -> Stream.of(WALL_SIDE_MID_LEFT);
            case 0b101_101_000 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b001_001_101 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b011_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder());
            case 0b110_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_101_101 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_101_100 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_INNER_CORNER_L_TOP_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_001 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_101_100 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_000 -> extraCases(cell,
                                             with(WALL_SIDE_FRONT_LEFT),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_LEFT));
            case 0b011_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_CORNER_RIGHT),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));

            case 0b100_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT));
            case 0b001_001_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             with(WALL_INNER_CORNER_MID_RIGTH));
            case 0b110_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder());

            case 0b100_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT));
            case 0b111_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_100 -> extraCases(cell,
                                             with(WALL_LEFT),
                                             with(WALL_LEFT, WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             with(WALL_LEFT, WALL_TOP_LEFT));
            case 0b001_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT));


            case 0b101_100_100, 0b101_000_000 -> Stream.of(WALL_SIDE_FRONT_LEFT);
            case 0b001_000_000 -> extraCases(cell,
                                             with(WALL_SIDE_FRONT_LEFT),
                                             with(WALL_SIDE_TOP_LEFT),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT),
                                             with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT)
            );


            case 0b000_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder()
            );


            case 0b000_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_RIGHT),
                                             placeHolder(),
                                             with(WALL_RIGHT)
            );
            case 0b101_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder()
            );
            case 0b111_101_000 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_MID,WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_001_100 -> extraCases(cell,
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_INNER_CORNER_MID_RIGTH),
                                             with(WALL_INNER_CORNER_MID_RIGTH)
            );
            case 0b011_000_100 -> extraCases(cell,
                                             with(WALL_MID),
                                             with(WALL_MID,WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT)
            );
            case 0b111_100_000 -> extraCases(cell,
                                             with(WALL_LEFT),
                                             with(WALL_CORNER_FRONT_LEFT, WALL_SIDE_TOP_LEFT),
                                             with(WALL_CORNER_FRONT_LEFT,
                                                  WALL_CORNER_TOP_RIGHT),
                                             with(WALL_LEFT, WALL_TOP_LEFT)
            );
            case 0b011_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(WALL_LEFT)
            );
            case 0b111_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b100_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_100_101 -> extraCases(cell,
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_SIDE_MID_LEFT),
                                             with(WALL_CORNER_BOTTOM_RIGHT),
                                             with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_000_000 -> extraCases(cell,
                                             with(WALL_MID),
                                             with(WALL_MID, WALL_SIDE_TOP_LEFT),
                                             with(WALL_MID, WALL_TOP_MID),
                                             with(WALL_MID, WALL_TOP_MID)
            );
            case 0b100_000_111 -> extraCases(cell,
                                             with(WALL_MID),
                                             placeHolder(),
                                             with(WALL_MID),
                                             with(WALL_MID));


            default -> getDefault();
        };
    }

}
