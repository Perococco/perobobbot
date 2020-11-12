package perobobbot.overlay.newtek;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.Consumer1;

import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@RequiredArgsConstructor
public class IntBufferCopier implements Consumer1<ByteBuffer> {

    @NonNull
    private final DataBufferInt image;

    @Override
    public void f(@NonNull ByteBuffer byteBuffer) {
        f(byteBuffer.asIntBuffer());
    }

    private void f(@NonNull IntBuffer intBuffer) {
        intBuffer.position(0);
        intBuffer.put(image.getData());
        intBuffer.flip();
    }
}
