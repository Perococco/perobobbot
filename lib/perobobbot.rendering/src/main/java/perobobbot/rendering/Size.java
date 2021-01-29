package perobobbot.rendering;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.MathTool;

@Value
public class Size {

    public static Size _0_0 = new Size(0,0);

    public static @NonNull Size with(double width, double height) {
        return new Size(width, height);
    }

    double width;

    double height;


    public @NonNull Size flipHeightWithWidth() {
        return with(height, width);
    }

    public @NonNull Size scale(double scale) {
        return with(width * scale, height * scale);
    }


    public Size addMargin(int margin) {
        return with(width+margin,height+margin);
    }

    public int getWidthAsInt() {
        return MathTool.roundedToInt(width);
    }

    public int getHeightAsInt() {
        return MathTool.roundedToInt(height);
    }

}
