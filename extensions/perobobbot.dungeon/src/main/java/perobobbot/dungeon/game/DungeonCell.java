package perobobbot.dungeon.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.CellType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DungeonCell implements Cell {

    @Getter
    private final @NonNull CellType type;

    private Tile central;
    private Map<Direction, Tile> neighbourTile = new HashMap<>();

    public DungeonCell(@NonNull CellType type) {
        this.type = type;
        if (type.isFloor()) {
            central = DungeonTile.pickOneFloor();
        } else {
            central = null;
        }
    }

    public Optional<Tile> getCentralTile() {
        return Optional.ofNullable(central);
    }

    public Optional<Tile> getTile(@NonNull Direction direction) {
        return Optional.ofNullable(neighbourTile.get(direction));
    }

}
