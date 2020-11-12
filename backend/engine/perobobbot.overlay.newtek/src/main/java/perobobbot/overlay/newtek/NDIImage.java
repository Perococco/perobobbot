package perobobbot.overlay.newtek;

import com.walker.devolay.DevolayFrameFourCCType;
import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Consumer2;
import perobobbot.rendering.Size;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class NDIImage extends BufferedImage {

    @NonNull
    public static NDIImage create(@NonNull Size size, DevolayFrameFourCCType ccType) {
        final Consumer2<int[],IntBuffer> copier = createCopier(ccType);
        return new NDIImage(size,copier);
    }

    private final @NonNull Consumer1<IntBuffer> copier;

    private final @NonNull Size overlaySize;

    private NDIImage(@NonNull Size overlaySize, @NonNull Consumer2<int[],IntBuffer> copier) {
        super(overlaySize.getWidth(), overlaySize.getHeight(), TYPE_INT_ARGB);
        this.overlaySize = overlaySize;
        this.copier = copier.f1(((DataBufferInt) getRaster().getDataBuffer()).getData());
    }

    public void copyTo(@NonNull ByteBuffer byteBuffer) {
        final IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.position(0);
        copier.f(intBuffer);
        intBuffer.flip();
    }

    @NonNull
    private static Consumer2<int[],IntBuffer> createCopier(@NonNull DevolayFrameFourCCType ccType) {
        return switch (ccType) {
            case BGRA -> (src,tgt) -> {
                for (int argb : src) {
                    int r = (argb&0x00FF0000);
                    int g = (argb&0x0000FF00);
                    int b = (argb&0x000000FF);
                    int bgra = (argb >>> 24) |(b<<24)|(g<<8)|(r>>>8);
                    tgt.put(bgra);
                }
            };
            case RGBA -> (src,tgt) -> {
                for (int argb : src) {
                    int rgba = (argb >>> 24) | (argb << 8);
                    tgt.put(rgba);
                }
            };
            default -> throw new IllegalArgumentException("Invalid frame color type " + ccType);
        };
    }
}
