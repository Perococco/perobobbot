package perobobbot.dungeon.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.CellType;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DungeonCell implements Cell {

    /**
     * The type of the cell, Set by the dungeon generator
     */
    @Getter
    private final @NonNull CellType type;

    @Getter @Setter
    private int flag;

    @Getter @Setter
    private ExtraFlag extraFlag;

    /**
     * The list of the tiles used to draw this cell
     */
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


    public String getFlagAsString() {
        final IntFunction<String> toBin = v -> Integer.toString((v&0b111)+8, 2).substring(1);
        return IntStream.of(flag>>6, flag>>3, flag)
                        .mapToObj(toBin)
                        .collect(Collectors.joining("_", "0b", " "+extraFlag.name()));

    }
}
