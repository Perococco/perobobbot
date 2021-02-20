package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.Layer;
import perobobbot.lang.fp.Function1;
import perococco.jdgen.api.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class DungeonTileInitializer {

    public static List<MissingPosition> initializeTiles(@NonNull DungeonMap map, @NonNull Random random) {
        return new DungeonTileInitializer(map,random).initializeTiles();
    }

    private final @NonNull DungeonMap map;
    private final @NonNull Random random;

    private final List<MissingPosition> missingPositions = new ArrayList<>();

    private List<MissingPosition> initializeTiles() {
        map.allMapPositions()
           .forEach(this::setTilesForOnePosition);
        return missingPositions;
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
        completeWallTiles(WallTileOnFloor::getWallTiles,p);
    }


    private void setEmptyTiles(Position p) {
        completeWallTiles(WallTileOnEmpty::getWallTiles,p);
    }

    private void setWallTiles(Position p) {
        completeWallTiles(WallTileOnWall::getWallTiles,p);
    }

    private void completeWallTiles(@NonNull Function1<DungeonCell,WallTiles> wallTilesGetter,
                                   @NonNull Position position) {
        final var cell = map.getCellAt(position);
        final var tiles = wallTilesGetter.apply(cell);
        if (tiles.isPlaceHolder()) {
            missingPositions.add(new MissingPosition(cell.getType().name(),position,cell.getFlagAsString()));
        }
        tiles.tileStream().forEach(t -> cell.addTile(Layer.WALL,t));
    }

}
