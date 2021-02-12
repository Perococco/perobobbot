package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.entity.Direction;
import perobobbot.lang.Todo;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Position;

import java.util.stream.Stream;

public class WallTileOnWall extends WallTileBase {

    public static @NonNull Stream<Tile> getWallTiles(@NonNull DungeonMap dungeonMap, @NonNull Position position) {
        return new WallTileOnWall(dungeonMap, position).getWallTiles();
    }

    @Getter
    private final @NonNull DungeonMap dungeonMap;
    @Getter
    private final @NonNull Position position;

    public WallTileOnWall(@NonNull DungeonMap dungeonMap, @NonNull Position position) {
        super(DungeonTile.COIN_ANIM_0, DungeonTile.FLASK_BIG_RED);
        this.dungeonMap = dungeonMap;
        this.position = position;
    }

    private @NonNull Stream<Tile> getWallTiles() {
        final var cell = dungeonMap.getCellAt(position);
        final var flag = cell.getFlag();
        return switch (flag) {

            case 0b100_000_100 -> Stream.empty();
            case 0b100_000_000 -> forCase100_000_000(cell);
            case 0b000_000_100 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_LEFT));
            case 0b111_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             with(DungeonTile.WALL_CORNER_RIGHT));
            case 0b101_101_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_INNER_CORNER_L_TOP_RIGTH),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b100_100_100 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b100_100_000 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b000_100_100 -> extraCases(cell,
                                             with(),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b101_100_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID)
            );
            case 0b100_100_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_FRONT_LEFT)
            );
            case 0b111_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder()
            );
            case 0b110_000_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_FRONT_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_L_TOP_RIGTH),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b101_001_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b110_000_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_RIGHT),
                                             with(DungeonTile.WALL_RIGHT, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_RIGHT, DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_RIGHT, DungeonTile.WALL_TOP_LEFT));
            case 0b001_001_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_INNER_CORNER_L_TOP_RIGTH),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_001_001 -> forCase000_001_000(cell);
            case 0b111_001_001 -> forCase111_001_001(cell);
            case 0b000_000_001 -> forCase000_000_001(cell);
            case 0b001_001_001 -> forCase001_001_001(cell);
            case 0b101_101_101 -> forCase101_101_101(cell);
            case 0b111_101_101 -> forCase111_101_101(cell);
            case 0b111_100_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_LEFT),
                                             with(DungeonTile.WALL_LEFT, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_LEFT, DungeonTile.WALL_TOP_LEFT),
                                             with(DungeonTile.WALL_LEFT, DungeonTile.WALL_TOP_LEFT)
            );
            case 0b100_101_001 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b110_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b111_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID,DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_LEFT)
            );

            case 0b110_100_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_RIGHT),
                                             with(DungeonTile.WALL_RIGHT, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_RIGHT, DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_TOP_LEFT)
            );
            case 0b011_000_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_MID),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_TOP_MID)
            );
            case 0b011_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_001_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_RIGHT)
            );
            case 0b011_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder());
            case 0b101_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b110_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder()
            );

            case 0b000_000_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b100_100_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b011_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_001_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b100_000_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);
            case 0b111_101_001 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b110_100_110 -> Stream.of(DungeonTile.WALL_CORNER_RIGHT);

            case 0b000_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b001_001_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID));
            case 0b001_100_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT, DungeonTile.WALL_TOP_LEFT));
            case 0b110_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_LEFT));
            case 0b000_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );

            case 0b111_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b101_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b000_001_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);
            case 0b001_101_111 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_RIGHT);

            case 0b000_000_011 -> Stream.of(DungeonTile.WALL_CORNER_FRONT_LEFT);

            case 0b100_101_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b011_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b001_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b111_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b011_001_011 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b001_001_011 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b110_100_000 -> Stream.of(DungeonTile.WALL_RIGHT);
            case 0b101_001_111 -> Stream.of(DungeonTile.WALL_RIGHT);


            case 0b011_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b000_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b110_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b001_000_011 -> Stream.of(DungeonTile.WALL_MID);
            case 0b100_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_100_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b000_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b111_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b110_000_111 -> Stream.of(DungeonTile.WALL_MID);
            case 0b001_000_111 -> Stream.of(DungeonTile.WALL_MID);


            case 0b001_000_101 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b100_000_001 -> Stream.of(DungeonTile.WALL_SIDE_MID_LEFT);
            case 0b101_101_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b001_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b011_001_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder());
            case 0b001_101_101 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b101_101_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_INNER_CORNER_L_TOP_RIGTH),
                                             placeHolder()
                                             );
            case 0b101_001_001 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b000_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_101_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b101_100_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT,DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             placeHolder());
            case 0b011_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder());
            case 0b111_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder());
            case 0b100_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_LEFT));
            case 0b110_000_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder());
            case 0b101_001_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder());

            case 0b100_101_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_LEFT));
            case 0b111_100_101 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_CORNER_BOTTOM_RIGHT));
            case 0b111_000_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_LEFT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder());
            case 0b001_000_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             placeHolder(),
                                             placeHolder());


            case 0b101_100_100 -> Stream.of(DungeonTile.WALL_SIDE_FRONT_LEFT);
            case 0b101_000_000 -> Stream.of(DungeonTile.WALL_SIDE_FRONT_LEFT);
            case 0b001_000_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT),
                                             with(DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT, DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_SIDE_FRONT_LEFT, DungeonTile.WALL_CORNER_TOP_RIGHT)
            );


            case 0b000_100_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder()
            );


            case 0b000_101_111 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_RIGHT),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_101_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_001_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_CORNER_RIGHT),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b001_000_110 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder()
            );
            case 0b011_000_100 -> extraCases(cell,
                                             with(DungeonTile.WALL_MID),
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b111_100_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_LEFT),
                                             with(DungeonTile.WALL_CORNER_FRONT_LEFT, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_CORNER_FRONT_LEFT, DungeonTile.WALL_CORNER_TOP_RIGHT),
                                             with(DungeonTile.WALL_LEFT, DungeonTile.WALL_TOP_LEFT)
            );
            case 0b011_000_011 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_LEFT)
            );
            case 0b111_100_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.CHEST_EMPTY_OPEN_ANIM_0),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_001_001 -> extraCases(cell,
                                             placeHolder(),
                                             with(DungeonTile.WALL_INNER_CORNER_MID_RIGTH),
                                             placeHolder(),
                                             placeHolder()
            );
            case 0b100_100_101 -> extraCases(cell,
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_SIDE_MID_LEFT),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT),
                                             with(DungeonTile.WALL_CORNER_BOTTOM_RIGHT)
            );
            case 0b111_000_000 -> extraCases(cell,
                                             with(DungeonTile.WALL_MID),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_SIDE_TOP_LEFT),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_TOP_MID),
                                             with(DungeonTile.WALL_MID, DungeonTile.WALL_TOP_MID)
            );
            case 0b100_000_111 -> extraCases(cell,
                                             placeHolder(),
                                             placeHolder(),
                                             placeHolder(),
                                             with(DungeonTile.WALL_MID));


            default -> getDefault();
        };
    }

    private Stream<Tile> forCase111_100_100(DungeonCell cell) {
        return Todo.TO_REMOVE();
    }

    private Stream<Tile> forCase100_101_001(DungeonCell cell) {
        return Todo.TO_REMOVE();
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
                return Stream.of(DungeonTile.WALL_INNER_CORNER_T_TOP_RIGHT);
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
