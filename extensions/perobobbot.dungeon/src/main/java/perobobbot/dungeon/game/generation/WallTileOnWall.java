package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.dungeon.game.DungeonCell;

import static perobobbot.dungeon.game.DungeonTile.*;

public class WallTileOnWall extends WallTileBase {

    public static @NonNull WallTiles getWallTiles(@NonNull DungeonCell cell) {
        return new WallTileOnWall(cell).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonCell cell;

    public WallTileOnWall(@NonNull DungeonCell cell) {
        super(FLASK_BLUE);
        this.cell = cell;
    }

    private @NonNull WallTiles getWallTiles() {
        final var flag = cell.getFlag();
        return switch (flag) {

            case 0b000_000_110 -> extraCases(placeHolder(), placeHolder(), with(WALL_CORNER_RIGHT), with(WALL_CORNER_RIGHT));

            case 0b100_000_110 -> extraCases(placeHolder(),placeHolder(),with(WALL_CORNER_RIGHT),with(WALL_CORNER_RIGHT));

            case 0b110_100_000, 0b001_000_101 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), placeHolder());
            case 0b001_000_011 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), with(WALL_CORNER_FRONT_LEFT));
            case 0b101_100_100 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT), placeHolder(), placeHolder(), placeHolder());
            case 0b101_000_000 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT), placeHolder(), placeHolder(), placeHolder());
            case 0b111_101_111 -> extraCases(
                    placeHolder(), with(WALL_RIGHT), placeHolder(), with(WALL_RIGHT));

            case 0b110_100_111 -> extraCases(
                    with(WALL_MID), placeHolder(), placeHolder(),  with(WALL_MID));


            case 0b101_001_111 -> extraCases(
                    with(WALL_RIGHT), placeHolder(), placeHolder(), with(WALL_RIGHT));

            case 0b101_101_111 -> extraCases(
                    with(WALL_RIGHT), with(WALL_RIGHT), placeHolder(), with(WALL_RIGHT));

            case 0b100_000_001 -> extraCases(
                    placeHolder(), with(WALL_SIDE_MID_LEFT), placeHolder(), placeHolder());
            case 0b001_000_111 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), with(WALL_MID));
            case 0b011_000_111 -> extraCases(
                    placeHolder(), with(WALL_MID), placeHolder(), with(WALL_MID));
            case 0b111_000_111 -> extraCases(
                    with(WALL_MID), with(WALL_MID), with(WALL_MID), with(WALL_MID));
            case 0b111_001_111 -> extraCases(
                    placeHolder(), with(WALL_RIGHT), placeHolder(), with(WALL_RIGHT));
            case 0b100_000_100 -> extraCases(
                    with(), placeHolder(), placeHolder(), with(WALL_TOP_LEFT));
            case 0b001_001_011 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), with(WALL_RIGHT));
            case 0b111_100_111 -> extraCases(
                    with(WALL_MID), with(WALL_MID), placeHolder(), with(WALL_MID));
            case 0b110_000_111 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), with(WALL_MID));
            case 0b000_100_111 -> extraCases(
                    with(WALL_MID), with(WALL_MID), placeHolder(), with(WALL_MID));
            case 0b001_101_111, 0b100_101_111 -> extraCases(
                    with(WALL_RIGHT), with(WALL_RIGHT), placeHolder(), placeHolder());
            case 0b011_001_011 -> extraCases(
                    placeHolder(), placeHolder(), placeHolder(), with(WALL_RIGHT));
            case 0b100_100_110 -> extraCases(
                    placeHolder(), placeHolder(), with(WALL_CORNER_RIGHT), placeHolder());
            case 0b011_001_111 -> extraCases(placeHolder(), placeHolder(), placeHolder(), with(WALL_RIGHT));
            case 0b000_000_011 -> extraCases(
                    placeHolder(), placeHolder(), with(WALL_CORNER_FRONT_LEFT), with(WALL_CORNER_FRONT_LEFT));
            case 0b100_100_111 -> extraCases(
                    with(WALL_MID), with(WALL_MID), with(WALL_MID), with(WALL_MID));
            case 0b000_000_111 -> extraCases(
                    with(WALL_MID), with(WALL_MID), with(WALL_MID), with(WALL_MID));
            case 0b001_001_111 -> extraCases(with(WALL_RIGHT), with(WALL_RIGHT), with(WALL_RIGHT),
                                             with(WALL_RIGHT));
            case 0b100_001_101 -> extraCases(
                    placeHolder(),with(WALL_SIDE_MID_LEFT),placeHolder(),placeHolder()
            );
            case 0b110_101_111 -> extraCases(
                    placeHolder(),with(WALL_RIGHT),placeHolder(),placeHolder()
            );
            case 0b100_000_000 -> extraCases(
                    with(),
                    placeHolder(),
                    with(WALL_CORNER_TOP_RIGHT),
                    with(WALL_CORNER_TOP_RIGHT));
            case 0b000_000_100, 0b000_100_100, 0b100_100_000, 0b100_100_100 -> extraCases(
                    with(),
                    with(WALL_SIDE_TOP_LEFT),
                    with(WALL_CORNER_TOP_RIGHT),
                    with(WALL_TOP_LEFT));
            case 0b111_000_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    with(WALL_CORNER_RIGHT));
            case 0b101_101_001 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_111 -> extraCases(
                    with(WALL_MID),
                    with(WALL_MID),
                    placeHolder(),
                    with(WALL_MID)
            );
            case 0b100_100_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_CORNER_FRONT_LEFT)
            );
            case 0b001_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder()
            );
            case 0b111_100_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT)
            );
            case 0b001_101_100 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_000_100 -> extraCases(
                    with(WALL_CORNER_FRONT_RIGHT),
                    with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                    placeHolder(),
                    with(WALL_TOP_LEFT)
            );
            case 0b101_100_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b100_100_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_000 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b100_001_111 -> extraCases(
                    placeHolder(),
                    with(WALL_RIGHT),
                    placeHolder(),
                    with(WALL_RIGHT)
            );
            case 0b110_000_000 -> extraCases(
                    with(WALL_RIGHT),
                    with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                    with(WALL_RIGHT, WALL_CORNER_TOP_RIGHT),
                    with(WALL_RIGHT, WALL_TOP_LEFT));
            case 0b001_001_000 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_101_101 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_101_101 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b011_001_000 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    placeHolder(),
                    placeHolder());
            case 0b011_101_111 -> extraCases(
                    placeHolder(),
                    with(WALL_RIGHT),
                    placeHolder(),
                    placeHolder());
            case 0b011_100_100 -> extraCases(
                    with(WALL_LEFT),
                    placeHolder(),
                    placeHolder(),
                    placeHolder());
            case 0b101_000_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT));
            case 0b100_000_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder()
            );
            case 0b001_000_100 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT),
                    placeHolder(),
                    placeHolder(),
                    placeHolder()
            );
            case 0b111_001_001 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b000_000_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b000_001_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_001_001 -> extraCases(
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b001_001_001 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_100_100 -> extraCases(
                    with(WALL_LEFT),
                    with(WALL_LEFT, WALL_SIDE_TOP_LEFT),
                    with(WALL_LEFT, WALL_TOP_LEFT),
                    with(WALL_LEFT, WALL_TOP_LEFT)
            );
            case 0b100_101_001 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_CORNER_BOTTOM_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b110_000_001 -> extraCases(
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder(),
                    placeHolder());
            case 0b101_001_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder());
            case 0b101_001_100 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder(),
                    placeHolder());
            case 0b110_101_101 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b111_001_101 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT)
            );

            case 0b110_100_100 -> extraCases(
                    with(WALL_RIGHT),
                    with(WALL_RIGHT, WALL_SIDE_TOP_LEFT),
                    with(WALL_RIGHT, WALL_CORNER_TOP_RIGHT),
                    with(WALL_TOP_LEFT)
            );
            case 0b011_000_000 -> extraCases(
                    with(WALL_MID),
                    with(WALL_MID, WALL_SIDE_TOP_LEFT),
                    with(WALL_MID, WALL_CORNER_TOP_RIGHT),
                    with(WALL_MID, WALL_TOP_MID)
            );
            case 0b011_000_001 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_001_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_RIGHT)
            );
            case 0b011_000_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder());
            case 0b101_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b110_000_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    with(WALL_INNER_CORNER_MID_RIGTH)
            );

            case 0b011_001_001 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_001_000 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_101_001 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_100_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder());

            case 0b000_101_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b001_001_100 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder());
            case 0b101_000_111 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_MID));
            case 0b001_100_100 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_SIDE_FRONT_LEFT, WALL_TOP_LEFT));
            case 0b110_000_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT));
            case 0b000_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_001_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_001_101 -> extraCases(
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder(),
                    placeHolder());
            case 0b011_100_111 -> extraCases(
                    placeHolder(),
                    with(WALL_LEFT),
                    placeHolder(),
                    placeHolder());


            case 0b101_101_000 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b001_001_101 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b011_001_101 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder());
            case 0b110_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder(),
                    placeHolder());
            case 0b001_101_101 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_101_100 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_INNER_CORNER_L_TOP_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_001 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_000_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_101_100 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID,WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_000 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT),
                    with(WALL_SIDE_FRONT_LEFT, WALL_SIDE_TOP_LEFT),
                    with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT),
                    with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_LEFT));
            case 0b011_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    with(WALL_CORNER_RIGHT),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_101 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));

            case 0b100_000_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT));
            case 0b001_001_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    with(WALL_INNER_CORNER_MID_RIGTH));
            case 0b110_000_101 -> extraCases(
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder(),
                    placeHolder());
            case 0b101_001_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder());

            case 0b100_101_101 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    with(WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_001 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT));
            case 0b111_100_101 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_100 -> extraCases(
                    with(WALL_LEFT),
                    with(WALL_LEFT, WALL_SIDE_TOP_LEFT),
                    placeHolder(),
                    with(WALL_LEFT, WALL_TOP_LEFT));
            case 0b001_000_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder());
            case 0b111_100_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT));


            case 0b001_000_000 -> extraCases(
                    with(WALL_SIDE_FRONT_LEFT),
                    with(WALL_SIDE_TOP_LEFT),
                    with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT),
                    with(WALL_SIDE_FRONT_LEFT, WALL_CORNER_TOP_RIGHT)
            );


            case 0b000_100_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder()
            );


            case 0b000_101_111 -> extraCases(
                    placeHolder(),
                    with(WALL_RIGHT),
                    placeHolder(),
                    with(WALL_RIGHT)
            );
            case 0b101_000_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    placeHolder()
            );
            case 0b111_101_000 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_001_100 -> extraCases(
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    placeHolder(),
                    placeHolder()
            );
            case 0b001_000_110 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(WALL_INNER_CORNER_MID_RIGTH),
                    with(WALL_INNER_CORNER_MID_RIGTH)
            );
            case 0b011_000_100 -> extraCases(
                    with(WALL_MID),
                    with(WALL_MID, WALL_SIDE_TOP_LEFT),
                    placeHolder(),
                    placeHolder()
            );
            case 0b101_100_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT)
            );
            case 0b111_100_000 -> extraCases(
                    with(WALL_LEFT),
                    with(WALL_CORNER_FRONT_LEFT, WALL_SIDE_TOP_LEFT),
                    with(WALL_CORNER_FRONT_LEFT,
                         WALL_CORNER_TOP_RIGHT),
                    with(WALL_LEFT, WALL_TOP_LEFT)
            );
            case 0b011_000_011 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(WALL_LEFT)
            );
            case 0b111_100_001 -> extraCases(
                    placeHolder(),
                    with(WALL_CORNER_RIGHT),
                    placeHolder(),
                    with(WALL_MID, WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b100_001_001 -> extraCases(
                    placeHolder(),
                    with(WALL_SIDE_MID_LEFT),
                    placeHolder(),
                    placeHolder()
            );
            case 0b100_100_101 -> extraCases(
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_SIDE_MID_LEFT),
                    with(WALL_CORNER_BOTTOM_RIGHT),
                    with(WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_000_000 -> extraCases(
                    with(WALL_MID),
                    with(WALL_MID, WALL_SIDE_TOP_LEFT),
                    with(WALL_MID, WALL_TOP_MID),
                    with(WALL_MID, WALL_TOP_MID)
            );
            case 0b100_000_111 -> extraCases(
                    with(WALL_MID),
                    placeHolder(),
                    with(WALL_MID),
                    with(WALL_MID));

            case 0b000_001_111 -> extraCases(placeHolder(),with(WALL_RIGHT),placeHolder(),with(WALL_RIGHT));

            default -> extraCases(placeHolder(), placeHolder(), placeHolder(), placeHolder(),placeHolder());
        };
    }

}
