package perococco.perobobbot.common.sound;

import lombok.NonNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.common.sound.SoundManager;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import java.util.stream.Stream;

public class TestConversion {

    public static Stream<String> soundResources() {
        return Stream.of(
                "tone_16b_PCM.wav",
                "tone_24b_PCM.wav",
                "tone_32b_PCM.wav",
                "tone_32float.wav",
                "tone_64float.wav",
                "tone_standard.mp3",
                "tone_extreme.mp3",
                "tone_insane.mp3"
        );
    }

    @ParameterizedTest
    @MethodSource("soundResources")
    public void name(@NonNull String resourceName) throws IOException, UnsupportedAudioFileException, URISyntaxException {
        final AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_FLOAT,
                48000f,
                32,
                2,
                8,
                48000f,
                false
        );
        final SoundManager soundManager = new PerococcoSoundManager(audioFormat, InMemorySoundResource::create);
        final URL sound = TestConversion.class.getResource(resourceName);
        final UUID id = soundManager.registerSoundResource(sound);
        System.out.println(id);
    }

}
