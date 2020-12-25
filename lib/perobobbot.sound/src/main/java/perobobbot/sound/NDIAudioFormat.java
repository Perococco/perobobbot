package perobobbot.sound;

import javax.sound.sampled.AudioFormat;

public class NDIAudioFormat extends AudioFormat {
    public NDIAudioFormat(int sampleRate) {
        super(AudioFormat.Encoding.PCM_FLOAT,
                sampleRate,
                Float.SIZE,
                2,
                Float.BYTES * 2,
                sampleRate,
                false);
    }

    public int computeOneChannelSize(int totalNbBytes) {
        return totalNbBytes/(Float.BYTES*2);
    }
}
