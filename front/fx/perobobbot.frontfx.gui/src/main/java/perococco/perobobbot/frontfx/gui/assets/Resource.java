package perococco.perobobbot.frontfx.gui.assets;

import com.google.common.collect.ImmutableSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.Cache;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Log4j2
public enum Resource {
    LOGO_128x128("logo_icon_128x128.png"),
    LOGO_64x64("logo_icon_64x64.png"),
    LOGO_32x32("logo_icon_32x32.png"),
    LOGO_16x16("logo_icon_16x16.png"),
    MISSING_IMAGE("missing.png"),
    ;

    private final Cache<BufferedImage> bufferedImage;

    private final Cache<Image> fxImage;

    @NonNull
    private final String resourceName;

    Resource(@NonNull String name) {
        this.resourceName = name;
        bufferedImage = Cache.soft(() -> read(this));
        fxImage = bufferedImage.mapSoft(b -> SwingFXUtils.toFXImage(b, null));
    }

    /**
     * @return an input stream pointing to the resource
     */
    @NonNull
    public InputStream getAsStream() {
        return Resource.class.getResourceAsStream(resourceName);
    }

    /**
     * @return the resource as an image in a {@link BufferedImage} type
     */
    @NonNull
    public BufferedImage getAsBufferedImage() {
        return bufferedImage.get();
    }

    /**
     * @return the resource as an image in a {@link Image} type
     */
    @NonNull
    public Image getAsFXImage() {
        return fxImage.get();
    }


    /**
     * @param nameTester a resource filter based on their name
     * @return a stream of the resources whose name matches the provided predicate
     */
    @NonNull
    public static Stream<Resource> streamFilteredByName(@NonNull Predicate<String> nameTester) {
        return Holder.VALUES.stream()
                            .filter(r -> nameTester.test(r.resourceName));
    }

    @NonNull
    private static BufferedImage read(@NonNull Resource resource) {
        try {
            try (InputStream is = resource.getAsStream()) {
                return ImageIO.read(is);
            }
        } catch (Exception e) {
            LOG.error("Could not read resource '{}' as image",resource,e);
            if (resource == MISSING_IMAGE) {
                return new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
            }
            return Holder.IMAGE_FOR_MISSING_IMAGE;
        }
    }


    private static class Holder {

        private static final ImmutableSet<Resource> VALUES = ImmutableSet.copyOf(values());

        private static final BufferedImage IMAGE_FOR_MISSING_IMAGE = MISSING_IMAGE.getAsBufferedImage();

    }

}
