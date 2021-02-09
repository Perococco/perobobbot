package perobobbot.rendering.tile;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.rendering.Renderer;

import java.awt.image.BufferedImage;

public interface Tile {

    void render(@NonNull Renderer renderer, double x, double y);

    void render(@NonNull Renderer renderer, double x, double y, double width, double height);
}
