package perobobbot.connect4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.drawing.Token;
import perobobbot.lang.MathTool;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;

@RequiredArgsConstructor
public class Connect4Overlay implements OverlayClient {

    private final @NonNull Connect4Game connect4Game;

    private Size overlaySize;

    private int xOffset = 0;
    private int yOffset = 0;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        this.overlaySize = overlay.getOverlaySize();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        connect4Game.update(iteration.getDeltaTime());

        iteration.getRenderer().withPrivateContext(c -> {
            c.translate(xOffset, yOffset);
            connect4Game.streamTokens().forEach(t -> drawToken(c, t));

            final var bufferedImage = connect4Game.getGridImage();
            iteration.getRenderer().drawImage(bufferedImage, 0, 0);
        });
    }

    private void drawToken(Renderer renderingContext, Token token) {
        final var position = token.getPosition();
        renderingContext.setColor(token.getColor());
        renderingContext.fillCircle(MathTool.roundedToInt(position.getX()), MathTool.roundedToInt(position.getY()), token.getRadius());
    }
}
