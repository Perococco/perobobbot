package perobobbot.overlay.newtek;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.function.Function;

public class TestReading {

    @Test
    public void name() throws IOException, UnsupportedAudioFileException {

        final URL url = TestReading.class.getResource("tone_24b_PCM.wav");

        final var b1 = readAll(url,this::readAll1);
        final var b2 = readAll(url,this::readAll2);

        Assertions.assertTrue(notSame(b1,b2),"Bug JDK-8254742 has been resolved. AudioStreamUtils.readAllBytes should be deprecated, use InputStream#readAllBytes instead");

    }

    protected boolean notSame(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) {
            return true;
        }
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return true;
            }
        }
        return false;
    }

    private byte[] readAll(URL url, Function<InputStream,byte[]> reader) throws IOException, UnsupportedAudioFileException {
        try (var i = AudioSystem.getAudioInputStream(url)) {
            return reader.apply(i);
        }
    }

    private byte[] readAll1(InputStream inputStream) {
        try {
            return inputStream.readNBytes(529200);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private byte[] readAll2(InputStream inputStream) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[3840];
            int nbRead = 0;
            do {
                nbRead = inputStream.read(buffer);
                if (nbRead > 0) {
                    outputStream.write(buffer, 0, nbRead);
                }
            } while (nbRead > 0);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
