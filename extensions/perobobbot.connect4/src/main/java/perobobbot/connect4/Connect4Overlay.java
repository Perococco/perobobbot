package perobobbot.connect4;

import lombok.NonNull;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.Size;

import java.awt.image.BufferedImage;

public class Connect4Overlay implements OverlayClient {

    private final Connect4Grid connect4Grid = new Connect4Grid(40);

    private Size overlaySize;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        this.overlaySize = overlay.getOverlaySize();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final var bufferedImage = connect4Grid.getImage();
        iteration.getRenderer().drawImage(bufferedImage, overlaySize.getWidth()-bufferedImage.getWidth(),0);
    }
}
