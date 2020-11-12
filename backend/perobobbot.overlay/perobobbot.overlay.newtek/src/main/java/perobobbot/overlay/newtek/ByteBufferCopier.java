package perobobbot.overlay.newtek;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer1;

import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

@RequiredArgsConstructor
public class ByteBufferCopier implements Consumer1<ByteBuffer> {

    @NonNull
    private final DataBufferByte image;

    @Override
    public void f(@NonNull ByteBuffer byteBuffer) {
        byteBuffer.position(0);
        byteBuffer.put(image.getData());
        byteBuffer.flip();
    }
}
