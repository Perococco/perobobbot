package perobobbot.rendering.histogram;

import lombok.NonNull;
import perobobbot.rendering.Renderer;

public interface BarDrawer {

    void drawBar(@NonNull Renderer renderer, double barWidth, double barHeight, double percent);

}
