package perobobbot.overlay.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Ratio;

@RequiredArgsConstructor
public enum FrameRate {

    FPS_29_97(30_000,1001),
    FPS_30(30,1),
    FPS_60(60,1),
    FPS_90(90,1),
    ;

    @Getter
    private final Ratio ratio;

    FrameRate(int numerator, int denominator) {
        this.ratio = Ratio.create(numerator,denominator);
    }

    public double getDeltaT() {
        return 1./ratio.doubleValue();
    }

    public int getNumerator() {
        return ratio.getNumerator();
    }

    public int getDenominator() {
        return ratio.getDenominator();
    }
}
