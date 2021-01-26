package perobobbot.connect4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.game.Connect4Game;
import perobobbot.connect4.game.Token;
import perobobbot.lang.MathTool;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Renderer;

@RequiredArgsConstructor
public class Connect4Overlay implements OverlayClient {

    private final @NonNull Connect4Game connect4Game;

    private int xOffset = 0;
    private int yOffset = 0;

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        connect4Game.update(iteration.getDeltaTime());

        iteration.getRenderer()
                 .withPrivateContext(renderer -> {
                     renderer.translate(xOffset, yOffset);
                     new Drawing(connect4Game, renderer).draw();
                 });
    }

    @RequiredArgsConstructor
    private static class Drawing {

        private final @NonNull Connect4Game connect4Game;

        private final @NonNull Renderer renderer;

        public void draw() {
            connect4Game.forEachTokens(this::drawToken);
            drawGrid();
        }

        private void drawToken(Token token) {
            final var position = token.getPosition();
            renderer.setColor(token.getColor());
            renderer.fillCircle(MathTool.roundedToInt(position.getX()), MathTool.roundedToInt(position.getY()), token.getRadius());
        }

        private void drawGrid() {
            final var bufferedImage = connect4Game.getGridImage();
            renderer.drawImage(bufferedImage, 0, 0);
        }

    }

}
