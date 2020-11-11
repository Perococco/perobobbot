package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.overlay.api.OverlayRenderer;
import perobobbot.puckwar.physic.Game;

@RequiredArgsConstructor
public class PuckWarOverlay implements OverlayClient {

    private final @NonNull Game game;

    @Override
    public void initialize(@NonNull Overlay overlay) {}

    @Override
    public void dispose(@NonNull Overlay overlay) {
        game.dispose();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final OverlayRenderer renderer = iteration.getOverlayRenderer();
        final double dt = iteration.getDeltaTime();

        game.updateGame(dt);
        game.draw(iteration.getOverlayRenderer());
    }

}
