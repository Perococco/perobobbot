package newtek.perobobbot.overlay;

import lombok.NonNull;
import perobobbot.common.lang.Ratio;
import perobobbot.overlay.FrameRate;

public class AudioBufferSizeComputer {

    public static AudioBufferSizeComputer create(@NonNull FrameRate frameRate, int audioSamplingRate) {
        final Ratio ratio = frameRate.getRatio().invert().multiply(audioSamplingRate);
        return new AudioBufferSizeComputer(ratio);
    }

    private final int maxSize;

    private final int fractionNumerator;

    private final int fractionDenominator;

    public AudioBufferSizeComputer(Ratio fraction) {
        this.maxSize = fraction.ceil();
        final Ratio fracPart = fraction.getFracPart();
        this.fractionNumerator = fracPart.getNumerator();
        this.fractionDenominator = fracPart.getDenominator();
    }

    public int getAudioBufferSize(long index) {
        final long n = getModuloIndex(index);
        final boolean isMax;
        if (n == 0) {
            isMax = true;
        } else {
            final long num = fractionNumerator*n;
            final long v1 = num/fractionDenominator;
            final long v2 = (num-fractionNumerator)/fractionDenominator;
            isMax = v1 != v2;
        }
        if (isMax) {
            return maxSize;
        }
        return maxSize-1;
    }

    public long getModuloIndex(long index) {
        final long n = index%fractionDenominator;
        return n<0?n+fractionNumerator:n;
    }
}
