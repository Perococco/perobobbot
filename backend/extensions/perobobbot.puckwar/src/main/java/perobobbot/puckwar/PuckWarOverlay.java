package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.puckwar.game.PuckWarGame;

@RequiredArgsConstructor
public class PuckWarOverlay implements OverlayClient {

    private final @NonNull PuckWarGame puckWarGame;

    @Override
    public void initialize(@NonNull Overlay overlay) {}

    @Override
    public void dispose(@NonNull Overlay overlay) {
        puckWarGame.stop();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        puckWarGame.updateGame(iteration.getDeltaTime());
        puckWarGame.drawWith(iteration.getRenderer());
    }

}
