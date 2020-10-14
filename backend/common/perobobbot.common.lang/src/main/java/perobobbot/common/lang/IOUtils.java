package perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;


    @NonNull
    public static byte[] readAllBytes(@NonNull InputStream inputStream) throws IOException {
        return readNBytes(inputStream, Integer.MAX_VALUE);
    }

    @NonNull
    public static byte[] readNBytes(@NonNull InputStream inputStream, int len) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException("len < 0");
        }

        List<ReadBuffer> bufs = new ArrayList<>();
        int total = 0;
        int remaining = len;
        int nread;
        byte[] buf = null;
        do {
            if (buf == null) {
                buf = new byte[Math.min(DEFAULT_BUFFER_SIZE, remaining)];
            }
            nread = read(inputStream,buf,buf.length);

            if (nread > 0) {
                remaining -= nread;
                total += nread;
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                bufs.add(new ReadBuffer(buf,nread));
                buf = null;
            } else {
                remaining -= -nread;
                total += -nread;
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        } while (nread >= 0 && remaining > 0);

        if (bufs.isEmpty()) {
            return new byte[0];
        } else if (bufs.size() == 1) {
            final ReadBuffer rb = bufs.get(0);
            if (rb.nread == total && rb.buffer.length == rb.nread) {
                return rb.buffer;
            } else {
                return Arrays.copyOf(rb.buffer,total);
            }
        } else {
            final byte[] result = new byte[total];
            int offset = 0;
            for (ReadBuffer readBuffer : bufs) {
                if (readBuffer.nread == 0) {
                    continue;
                }
                System.arraycopy(readBuffer.buffer,0,result,offset,readBuffer.nread);
                offset+=readBuffer.nread;
            }
            return result;
        }
    }

    private static int read(@NonNull InputStream inputStream, byte[] target, int len) throws IOException {
        int remaining = len;
        int pos = 0;
        while (remaining>0) {
            int n = inputStream.read(target,pos,remaining);
            if (n<0) {
                return -pos;
            }
            else if (n == 0) {
                return pos;
            }
            remaining -= n;
            pos+=n;
        }
        return pos;
    }

    @RequiredArgsConstructor
    @Getter
    private static class ReadBuffer {

        @NonNull
        private final byte[] buffer;

        private final int nread;
    }
}
