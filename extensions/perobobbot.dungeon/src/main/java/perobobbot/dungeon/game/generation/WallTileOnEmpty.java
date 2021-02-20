package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
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

    public static @NonNull WallTiles getWallTiles(@NonNull DungeonCell cell) {
        return new WallTileOnEmpty(cell).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonCell cell;

    public WallTileOnEmpty(@NonNull DungeonCell cell) {
        super(DungeonTile.FLASK_BIG_GREEN);
        this.cell = cell;
    }


    private @NonNull WallTiles getWallTiles() {
        final var flag = cell.getFlag();
        return switch (flag) {
            case 0b000_000_000,
                    0b000_000_001,
                    0b000_000_100,
                    0b000_000_101,
                    0b000_000_110,
                    0b000_001_001,
                    0b000_001_101,
                    0b000_100_100,
                    0b000_100_101,
                    0b000_100_110,
                    0b000_101_101,
                    0b001_000_000,
                    0b001_000_001,
                    0b001_000_100,
                    0b001_000_101,
                    0b001_000_110,
                    0b001_001_000,
                    0b001_001_001,
                    0b001_001_100,
                    0b001_001_101,
                    0b001_001_110,
                    0b001_100_100,
                    0b001_101_100,
                    0b001_101_101,
                    0b011_000_000,
                    0b011_000_001,
                    0b011_000_100,
                    0b011_001_000,
                    0b011_001_001,
                    0b100_000_000,
                    0b100_000_001,
                    0b100_000_100,
                    0b100_000_110,
                    0b100_001_001,
                    0b100_100_000,
                    0b100_100_001,
                    0b100_100_100,
                    0b100_100_101,
                    0b100_100_110,
                    0b100_101_001,
                    0b100_101_101,
                    0b101_000_000,
                    0b101_000_100,
                    0b101_001_000,
                    0b101_001_001,
                    0b101_001_101,
                    0b101_100_000,
                    0b101_100_100,
                    0b101_100_110,
                    0b101_101_000,
                    0b101_101_001,
                    0b101_101_100,
                    0b101_101_101,
                    0b101_101_110,
                    0b110_000_000,
                    0b110_000_001,
                    0b110_000_100,
                    0b110_000_110,
                    0b110_001_001,
                    0b110_100_000,
                    0b110_100_100,
                    0b110_100_101,
                    0b110_100_110,
                    0b111_000_000,
                    0b111_000_001,
                    0b111_000_100,
                    0b111_000_101,
                    0b111_000_110,
                    0b111_001_000,
                    0b111_001_001,
                    0b111_001_101,
                    0b111_100_000,
                    0b111_100_100,
                    0b111_100_101,
                    0b111_100_110,
                    0b111_101_000,
                    0b111_101_001,
                    0b111_101_100,
                    0b111_101_101 -> extraCases(with(),
                                                placeHolder(),
                                                placeHolder(),
                                                placeHolder());
            case 0b110_000_111 -> extraCases(with(),
                                     placeHolder(),
                                     with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                     placeHolder());

            case 0b100_000_111 -> extraCases(with(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b111_101_011 -> extraCases(
                    placeHolder(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    placeHolder(),
                    placeHolder());
            case 0b111_000_011 -> extraCases(with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_001_011 -> extraCases(with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());
            case 0b000_100_111 -> extraCases(with(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_MID));
            case 0b000_000_011 -> extraCases(with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             placeHolder());

            case 0b001_000_011, 0b110_000_011, 0b101_001_011, 0b011_000_011, 0b111_001_011, 0b100_000_011, 0b011_001_011, 0b011_100_111, 0b100_100_011, 0b111_100_011 -> extraCases(
                    placeHolder(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    placeHolder(),
                    placeHolder()
            );


            case 0b111_100_111 -> extraCases(
                    with(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    with(DungeonTile.WALL_TOP_MID),
                    with(DungeonTile.WALL_TOP_MID)
            );

            case 0b011_000_111, 0b011_001_111 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID));
            case 0b111_000_111, 0b110_100_111, 0b100_100_111 -> extraCases(
                    placeHolder(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID));
            case 0b110_001_111, 0b101_100_111, 0b001_000_111, 0b001_101_111, 0b000_101_111 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(DungeonTile.WALL_TOP_MID));
            case 0b100_101_111 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    with(DungeonTile.WALL_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID));
            case 0b000_001_111, 0b101_001_111, 0b111_101_111 -> extraCases(
                    with(),
                    placeHolder(),
                    with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID));

            case 0b101_000_111 -> extraCases(
                    placeHolder(),
                    placeHolder(),
                    placeHolder(),
                    with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b001_001_111, 0b111_001_111, 0b101_101_111 -> extraCases(
                    with(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID));

            case 0b000_000_111 -> extraCases(
                    with(),
                    with(DungeonTile.WALL_SIDE_TOP_LEFT),
                    with(DungeonTile.WALL_INNER_CORNER_T_TOP_RIGHT),
                    with(DungeonTile.WALL_TOP_MID)
            );

            default -> extraCases(with(), with(), with(), with(), placeHolder());
        };
    }
}
