package perobobbot.dungeon.game;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.CellType;

import java.util.*;
import java.util.stream.Stream;

public class DungeonCell implements Cell {

    @Getter
    private final @NonNull CellType type;

    private Map<Layer,List<Tile>> tiles = new HashMap<>();

    public DungeonCell(@NonNull CellType type) {
        this.type = type;
    }

    public void addTile(@NonNull Layer layer, @NonNull Tile tile) {
        tiles.computeIfAbsent(layer, l -> new ArrayList<>()).add(tile);
    }

    public void addFirstTile(@NonNull Layer layer, Tile tile) {
        tiles.computeIfAbsent(layer, l -> new ArrayList<>()).add(0,tile);
    }

    public @NonNull Stream<Tile> getTile(@NonNull Layer layer) {
        return Optional.ofNullable(tiles.get(layer))
                       .stream()
                       .flatMap(Collection::stream);
    }

}
