package perococco.perobobbot.common.sound;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.IOUtils;
import perobobbot.common.sound.Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InMemorySoundResource implements SoundResource {

    @NonNull
    public static SoundResource create(@NonNull URL url, @NonNull AudioFormat audioFormat) throws IOException, UnsupportedAudioFileException {
            final AudioFileFormat f = AudioSystem.getAudioFileFormat(url);
            final AudioInputStream input = AudioSystem.getAudioInputStream(url);
            final AudioFormat fi = input.getFormat();
            final AudioInputStream stream = AudioSystem.getAudioInputStream(audioFormat,input);
            final byte[] in = IOUtils.readAllBytes(AudioSystem.getAudioInputStream(url));
            final byte[] data = IOUtils.readAllBytes(stream);
            System.out.println(data.length);
            System.out.println(in.length+ " " +f);
            System.out.println(stream.getFormat());
            return new InMemorySoundResource(data);
    }

    private final byte[] data;

    @Override
    public @NonNull Sound createSound() {
        throw new RuntimeException("Not Yet Implemented");
    }
}
