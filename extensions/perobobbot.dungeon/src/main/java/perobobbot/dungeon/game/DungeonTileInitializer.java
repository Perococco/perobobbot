package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.CellType;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;

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
            case DOOR,CELL_FLOOR,ROOM_FLOOR,CORRIDOR_FLOOR -> setFloorTiles(p);
            case EMPTY -> setEmptyTiles(p);
            case WALL -> setWallTiles(p);
        }
    }

    private void setFloorTiles(@NonNull Position p) {
        final var cell = map.getCellAt(p);
        cell.addCentralTile(DungeonTile.pickOneFloor());

        for (Direction direction : Direction.allDirections()) {
            final var neighbour = map.getCellAt(direction.moveByOne(p));
            if (neighbour.getType() == CellType.WALL) {
                DungeonTile.pickOneWall(direction).ifPresent(t -> cell.addNeighbourTile(direction,t));
            }
        }

    }


    private void setEmptyTiles(Position p) {
        //do nothing
    }

    private void setWallTiles(Position p) {
        //do nothing
    }
}
