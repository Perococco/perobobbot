package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Temporal;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;

@RequiredArgsConstructor
public class GameController implements Temporal {

    private final @NonNull Dungeon dungeon;

    @Override
    public void initialize(double t0) {
        this.dungeon.initialize(t0);
    }

    @Override
    public void update(double dt) {
        this.dungeon.update(dt);
    }

    public void drawWith(@NonNull Renderer renderer,@NonNull Size size) {
        DungeonDrawer.render(dungeon,renderer, size);
    }
}
