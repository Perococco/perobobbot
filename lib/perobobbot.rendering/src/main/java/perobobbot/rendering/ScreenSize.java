package perobobbot.rendering;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.lang.MathTool;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreenSize {

    public static ScreenSize with(int width, int height) {
        if (width == 1920 && height == 1080) {
            return _1920_1080;
        }
        if (width == 1600 && height == 900) {
            return _1600_900;
        }
        return new ScreenSize(width, height);
    }

    public static final ScreenSize _1920_1080 = new ScreenSize(1920, 1080);
    public static final ScreenSize _1600_900 = new ScreenSize(1600, 900);

    public static final ScreenSize _0_0 = new ScreenSize(0, 0);

    int width;
    int height;

    public int numberOfPixels() {
        return width*height;
    }

    public int getMinLength() {
        return Math.min(width,height);
    }

    public @NonNull ScreenSize flipHeightWithWidth() {
        return with(height,width);
    }

    public @NonNull ScreenSize scale(double scale) {
        return with(MathTool.roundedToInt(width*scale), MathTool.roundedToInt(height*scale) );
    }

    public @NonNull Size toSize() {
        return new Size(width,height);
    }
}
