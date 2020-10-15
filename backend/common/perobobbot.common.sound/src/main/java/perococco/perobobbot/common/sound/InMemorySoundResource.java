package perococco.perobobbot.common.sound;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.AudioStreamUtils;
import perobobbot.common.sound.Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemorySoundResource implements SoundResource {

    @NonNull
    public static SoundResource create(@NonNull URL url, @NonNull AudioFormat audioFormat) throws IOException, UnsupportedAudioFileException {
            final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFormat,AudioSystem.getAudioInputStream(url));
            final byte[] data = AudioStreamUtils.readAllBytes(stream);
            return new InMemorySoundResource(data);
    }

    private final byte[] data;

    @Override
    public @NonNull Sound createSound() {
        throw new RuntimeException("Not Yet Implemented");
    }
}
