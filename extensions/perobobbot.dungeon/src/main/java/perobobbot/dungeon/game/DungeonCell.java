package perobobbot.dungeon.game;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.CellType;

import java.util.*;
import java.util.stream.Stream;

public class DungeonCell implements Cell {

    @Getter
    private final @NonNull CellType type;

    private List<Tile> centralTiles = new ArrayList<>();
    private Map<Direction, List<Tile>> neighbourTile = new HashMap<>();

    public DungeonCell(@NonNull CellType type) {
        this.type = type;
    }

    public @NonNull Stream<Tile> getCentralTiles() {
        return centralTiles.stream();
    }

    public @NonNull Stream<Tile> getTiles(@NonNull Direction direction) {
        return neighbourTile.getOrDefault(direction,List.of()).stream();
    }

    public void addCentralTile(@NonNull Tile tile) {
        this.centralTiles.add(tile);
    }

    public void addNeighbourTile(@NonNull Direction direction, @NonNull Tile tile) {
        this.neighbourTile.computeIfAbsent(direction,d -> new ArrayList<>()).add(tile);
    }

}
