package perobobbot.rendering.histogram;

import lombok.NonNull;

import java.awt.*;

public interface PaintComputer {

    @NonNull Paint compute(double barWidth, double barHeight, double histogramHeight);

}
