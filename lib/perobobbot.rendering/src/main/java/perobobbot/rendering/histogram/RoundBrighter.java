package perobobbot.rendering.histogram;

import lombok.NonNull;

import java.awt.*;

public class RoundBrighter implements PaintComputer {

    private final Color reference;
    private final Color referenceBrighter;

    private final float[] fractions = {0,0.4f,0.6f,1};
    private final Color[] colors;

    public RoundBrighter(Color reference) {
        this.reference = reference;
        this.referenceBrighter = reference.brighter();
        this.colors = new Color[]{reference,referenceBrighter,referenceBrighter,reference};
    }


    public @NonNull BarDrawer createBarDrawer() {
        return new SimpleBarDrawer(this);
    }

    @Override
    public @NonNull Paint compute(double barWidth, double barHeight, double histogramHeight) {
        return new LinearGradientPaint(0,0,(float)barWidth,0,fractions,colors);
    }
}
