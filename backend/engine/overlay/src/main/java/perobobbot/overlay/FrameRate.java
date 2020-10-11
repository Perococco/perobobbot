package perobobbot.overlay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FrameRate {

    FPS_29_97(30_000,1001),
    FPS_30(30,1),
    FPS_60(60,1),
    FPS_90(90,1),
    ;

    @Getter
    private final int numerator;

    @Getter
    private final int denominator;

    public double getDeltaT() {
        return ((double)denominator)/numerator;
    }
}
