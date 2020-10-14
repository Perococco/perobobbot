package perobobbot.common.sound;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.AudioFormat;

@UtilityClass
public class AudioFormats {

    @NonNull
    public static AudioFormat stereo32Float(float sampleRate) {
        return stereoFloat(sampleRate, 32);
    }

    public static AudioFormat stereoFloat(float sampleRate, int sampleSizeInBits) {
        final int nbSamplePerFrame = 1;
        final int nbChannels = 2;
        final int frameSize = (sampleSizeInBits / 8) * nbChannels * nbSamplePerFrame;
        final float frameRate = sampleRate / nbSamplePerFrame;
        return new AudioFormat(
                AudioFormat.Encoding.PCM_FLOAT,
                sampleRate,
                sampleSizeInBits,
                nbChannels,
                frameSize,
                frameRate,
                false
        );

    }
}
