package perobobbot.rendering.tile;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Animation;
import perobobbot.rendering.Renderable;

@RequiredArgsConstructor
public class TileBasedAnimation implements Animation {

    public static @NonNull TileBasedAnimation with(@NonNull Tile... tiles) {
        return new TileBasedAnimation(ImmutableList.copyOf(tiles));
    }

    private final @NonNull ImmutableList<Tile> tiles;


    @Override
    public int getNbFrames() {
        return tiles.size();
    }

    @Override
    public @NonNull Renderable getFrame(int index) {
        return tiles.get(index);
    }
}
