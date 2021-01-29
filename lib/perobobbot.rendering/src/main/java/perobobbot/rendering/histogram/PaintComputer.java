package perobobbot.rendering.histogram;

import lombok.NonNull;

import java.awt.*;

public interface PaintComputer {

    @NonNull Paint compute(int barWidth, int barHeight, float histogramHeight);

}
