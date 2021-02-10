package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class DungeonTileInitializer {

    public static void initializeTiles(@NonNull Map<DungeonCell> map) {
        new DungeonTileInitializer(map).initializeTiles();
    }

    private final @NonNull Map<DungeonCell> map;


    private void initializeTiles() {
        map.allMapPositions()
           .forEach(this::setTileAtPosition);
    }

    private void setTileAtPosition(@NonNull Position p) {
        final var cell = map.getCellAt(p);
        switch (cell.getType()) {
            case DOOR, CELL_FLOOR, ROOM_FLOOR, CORRIDOR_FLOOR -> setFloorTiles(p);
            case EMPTY -> setEmptyTiles(p);
            case WALL -> setWallTiles(p);
        }
    }

    private void setFloorTiles(@NonNull Position p) {
        final var cell = map.getCellAt(p);
        cell.addFirstTile(Layer.FLOOR, DungeonTile.pickOneFloor());

    }


    private void setEmptyTiles(Position p) {
        //do nothing
    }

    private void setWallTiles(Position p) {
        final var northTile = Direction.NORTH.moveByOne(p);
        final var southTile = Direction.SOUTH.moveByOne(p);
        final var westTile = Direction.WEST.moveByOne(p);
        final var eastTile = Direction.EAST.moveByOne(p);
        final var northIsFloor = isPositionFloor(northTile);
        final var southIsFloor = isPositionFloor(southTile);
        final var eastIsFloor = isPositionFloor(eastTile);
        final var westIsFloor = isPositionFloor(westTile);

        if (southIsFloor&&northIsFloor&&eastIsFloor&&westIsFloor) {
            map.getCellAt(northTile).addTile(Layer.WALL,DungeonTile.COLUMN_TOP);
            map.getCellAt(p).addTile(Layer.WALL,DungeonTile.FLOOR_1);
            map.getCellAt(p).addTile(Layer.WALL,DungeonTile.COLUMN_MID);
            map.getCellAt(southTile).addTile(Layer.FLOOR,DungeonTile.COLUMN_BASE);
        }
        else {
            if (southIsFloor || northIsFloor) {
                if (eastIsFloor && westIsFloor) {
                    map.getCellAt(northTile).addTile(Layer.WALL,DungeonTile.WALL_TOP_RIGHT); //TODO create a tile for this specific case
                } else if (eastIsFloor) {
                    map.getCellAt(northTile).addTile(Layer.WALL,DungeonTile.WALL_INNER_CORNER_T_TOP_RIGTH);
                    map.getCellAt(p).addTile(Layer.WALL,DungeonTile.WALL_RIGHT);
                } else if (westIsFloor) {
                    map.getCellAt(northTile).addTile(Layer.WALL,DungeonTile.WALL_INNER_CORNER_T_TOP_LEFT);
                    map.getCellAt(p).addTile(Layer.WALL,DungeonTile.WALL_LEFT);
                } else {
                    map.getCellAt(northTile).addTile(Layer.WALL,DungeonTile.WALL_TOP_MID);
                    map.getCellAt(p).addTile(Layer.WALL,DungeonTile.WALL_MID);
                }
            }

            if (eastIsFloor) {
                if (southIsFloor) {
                    map.getCellAt(northTile).addTile(Layer.WALL, DungeonTile.WALL_CORNER_BOTTOM_RIGHT);
                } else {
                    map.getCellAt(p).addTile(Layer.WALL, DungeonTile.WALL_SIDE_MID_LEFT);
                }
            }

            if (westIsFloor) {
                if (southIsFloor) {
                    map.getCellAt(northTile).addTile(Layer.WALL, DungeonTile.WALL_CORNER_BOTTOM_LEFT);
                } else {
                    map.getCellAt(p).addTile(Layer.WALL, DungeonTile.WALL_SIDE_MID_RIGHT);
                }
            }
        }
    }

    private boolean isPositionFloor(@NonNull Position position) {
        return map.getCellAt(position).isFloor();
    }
    private boolean isPositionWall(@NonNull Position position) {
        return map.getCellAt(position).getType() == CellType.WALL;
    }
}
