package perobobbot.rendering.histogram;

import lombok.NonNull;
import perobobbot.rendering.Renderer;

public interface BarDrawer {

    void drawBar(@NonNull Renderer renderer, int barWidth, int barHeight, double percent);

}
