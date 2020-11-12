package perobobbot.lang;

import lombok.NonNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioStreamUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;


    @NonNull
    public static byte[] readAllBytes(@NonNull AudioInputStream inputStream) throws IOException {
        return readNBytes(inputStream, Integer.MAX_VALUE);
    }

    private static int getFrameSize(@NonNull AudioInputStream inputStream) {
        final int frameSize = inputStream.getFormat().getFrameSize();
        return frameSize == AudioSystem.NOT_SPECIFIED?1:frameSize;
    }

    private static int adaptLength(int length, int frameSize) {
        final int remain = length%frameSize;
        if (remain == 0) {
            return length;
        }
        return remain-length+frameSize;
    }

    @NonNull
    public static byte[] readNBytes(@NonNull AudioInputStream inputStream, int len) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException("len < 0");
        }

        final int frameSize = getFrameSize(inputStream);

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = len;
        int n;
        do {
            byte[] buf = new byte[adaptLength(Math.min(remaining, DEFAULT_BUFFER_SIZE),frameSize)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((n = inputStream.read(buf, nread, Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                total += nread;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        } while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ?
                    result : Arrays.copyOf(result, total);
        }

        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }
}
