package perobobbot.rendering.graphics2d.element;

import lombok.NonNull;
import perobobbot.rendering.ScreenSize;
import perobobbot.rendering.Size;

import java.awt.*;

public interface BlockElement {
    double getRequiredWidth();

    double getHeight();

    void draw(@NonNull Graphics2D graphics2D, @NonNull Size size);
}
