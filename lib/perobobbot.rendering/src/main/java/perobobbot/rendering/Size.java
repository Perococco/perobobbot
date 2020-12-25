package perobobbot.rendering;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Size {

    public static Size with(int width, int height) {
        if (width == 1920 && height == 1080) {
            return _1920_1080;
        }
        if (width == 1600 && height == 900) {
            return _1600_900;
        }
        return new Size(width,height);
    }

    public static final Size _1920_1080 = new Size(1920,1080);
    public static final Size _1600_900 = new Size(1600,900);

    public static final Size _0_0 = new Size(0,0);

    int width;
    int height;

    public int numberOfPixels() {
        return width*height;
    }

    public @NonNull Size addMargin(int margin) {
        return with(width+margin,height+margin);
    }

    public int getMinLength() {
        return Math.min(width,height);
    }
}
