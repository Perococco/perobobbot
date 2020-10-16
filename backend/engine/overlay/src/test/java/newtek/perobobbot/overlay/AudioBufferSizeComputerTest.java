package newtek.perobobbot.overlay;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.overlay.FrameRate;

import java.util.stream.Stream;

public class AudioBufferSizeComputerTest {


    public static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(FrameRate.FPS_29_97,48000,0,1602),
                Arguments.of(FrameRate.FPS_29_97,48000,1,1601),
                Arguments.of(FrameRate.FPS_29_97,48000,2,1602),
                Arguments.of(FrameRate.FPS_29_97,48000,3,1601),
                Arguments.of(FrameRate.FPS_29_97,48000,4,1602),
                Arguments.of(FrameRate.FPS_29_97,48000,5,1602),
                Arguments.of(FrameRate.FPS_29_97,48000,6,1601),
                Arguments.of(FrameRate.FPS_29_97,48000,7,1602),
                Arguments.of(FrameRate.FPS_29_97,48000,8,1601),
                Arguments.of(FrameRate.FPS_29_97,48000,9,1602),

                Arguments.of(FrameRate.FPS_30,48000,0,1600),
                Arguments.of(FrameRate.FPS_30,48000,1,1600),
                Arguments.of(FrameRate.FPS_30,48000,2,1600),
                Arguments.of(FrameRate.FPS_30,48000,3,1600),
                Arguments.of(FrameRate.FPS_30,48000,4,1600),
                Arguments.of(FrameRate.FPS_30,48000,5,1600),
                Arguments.of(FrameRate.FPS_30,48000,6,1600),
                Arguments.of(FrameRate.FPS_30,48000,7,1600),
                Arguments.of(FrameRate.FPS_30,48000,8,1600),
                Arguments.of(FrameRate.FPS_30,48000,9,1600)
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void test(@NonNull FrameRate frameRate, int audioSampleRate, long index, int expected) {
        final int actual = AudioBufferSizeComputer.create(frameRate,audioSampleRate).getAudioBufferSize(index);
        Assertions.assertEquals(expected,actual);
    }
}
