package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.Layer;
import perococco.jdgen.api.Position;

import java.util.Random;

@RequiredArgsConstructor
public class DungeonTileInitializer {

    public static void initializeTiles(@NonNull DungeonMap map, @NonNull Random random) {
        new DungeonTileInitializer(map,random).initializeTiles();
    }

    private final @NonNull DungeonMap map;
    private final @NonNull Random random;

    private void initializeTiles() {
        map.allMapPositions()
           .forEach(this::setTilesForOnePosition);
    }

    private void setTilesForOnePosition(@NonNull Position position) {
        final var cell = map.getCellAt(position);
        switch (cell.getType()) {
            case DOOR, CELL_FLOOR, ROOM_FLOOR, CORRIDOR_FLOOR -> setFloorTiles(position);
            case EMPTY -> setEmptyTiles(position);
            case WALL -> setWallTiles(position);
        }
    }

    private void setFloorTiles(@NonNull Position p) {
        final var cell = map.getCellAt(p);
        cell.addTile(Layer.FLOOR, DungeonTile.pickOneFloor(random));
        WallTileOnFloor.getWallTiles(map,p).forEach(t -> cell.addTile(Layer.WALL, t));
    }


    private void setEmptyTiles(Position p) {
        final var cell = map.getCellAt(p);
        WallTileOnEmpty.getWallTiles(map,p).forEach(t -> cell.addTile(Layer.WALL, t));
    }

    private void setWallTiles(Position p) {
        final var cell = map.getCellAt(p);
        WallTileOnWall.getWallTiles(map,p).forEach(t -> cell.addTile(Layer.WALL,t));
    }

}
