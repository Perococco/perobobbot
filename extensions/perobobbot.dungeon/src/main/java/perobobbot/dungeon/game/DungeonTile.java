package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.rendering.tile.Tile;
import perobobbot.rendering.tile.TileGeometry;

import java.util.Random;

@RequiredArgsConstructor
public enum DungeonTile implements Tile {
    FLOOR_1(16, 64, 16, 16),
    FLOOR_2(32, 64, 16, 16),
    FLOOR_3(48, 64, 16, 16),
    FLOOR_4(16, 80, 16, 16),
    FLOOR_5(32, 80, 16, 16),
    FLOOR_6(48, 80, 16, 16),
    FLOOR_7(16, 96, 16, 16),
    FLOOR_8(32, 96, 16, 16),
    ;

    @Delegate
    private final Tile delegate;


    DungeonTile(int x, int y, int width, int height) {
        this.delegate = DungeonTileSet.INSTANCE.extractTile(new TileGeometry(x, y, width, height));
    }

    private static final Random RANDOM = new Random();

    public static @NonNull Tile pickOneFloor() {
        return Holder.FLOORS[RANDOM.nextInt(Holder.FLOORS.length)];
    }

    private static class Holder {
        private static final Tile[] FLOORS = {FLOOR_1, FLOOR_2, FLOOR_3, FLOOR_4, FLOOR_5, FLOOR_6, FLOOR_7, FLOOR_8};
    }
}
