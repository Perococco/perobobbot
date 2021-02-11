package perobobbot.dungeon;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.Dungeon;
import perobobbot.dungeon.game.DungeonDrawer;
import perobobbot.dungeon.game.GameController;
import perobobbot.lang.Exec;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Region;
import perobobbot.rendering.Renderer;

@RequiredArgsConstructor
public class DungeonOverlay implements OverlayClient {

    private final @NonNull GameController gameController;

    private final @NonNull Region region;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        gameController.initialize(0);
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        updateDungeon(iteration.getDeltaTime());

        final var renderer = iteration.getRenderer();
        prepareDrawingArea(renderer);
        drawDungeon(renderer);
    }


    private void prepareDrawingArea(Renderer renderer) {
        renderer.translate(region.getX(), region.getY());
    }

    private void updateDungeon(double dt) {
        this.gameController.update(dt);
    }

    private void drawDungeon(@NonNull Renderer renderer) {
        gameController.drawWith(renderer,region.getSize());
    }
}
