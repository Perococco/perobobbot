package perobobbot.dungeon;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonDrawer;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Region;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;

@RequiredArgsConstructor
public class DungeonOverlay implements OverlayClient {

    private final @NonNull Map<DungeonCell> map;

    private final @NonNull Region region;

    @Override
    public void initialize(@NonNull Overlay overlay) {
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final var renderer = iteration.getRenderer();

        renderer.translate(region.getX(),region.getY());
        DungeonDrawer.render(map,
                             new Position(map.getSize().getWidth() / 2, map.getSize().getHeight() / 2),
                             renderer,
                             region.getSize()
        );
    }
}
