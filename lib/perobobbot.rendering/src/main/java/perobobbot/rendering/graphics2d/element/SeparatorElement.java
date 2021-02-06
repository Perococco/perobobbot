package perobobbot.rendering.graphics2d.element;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.MathTool;
import perobobbot.rendering.Size;

import java.awt.*;

@Value
@Getter
public class SeparatorElement implements BlockElement {

    /**
     * The width of the separator in fraction
     */
    float widthFraction;

    @NonNull Stroke stroke;

    @NonNull Color color;

    @Override
    public double getRequiredWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        if (stroke instanceof BasicStroke) {
            return ((BasicStroke) stroke).getLineWidth();
        } else {
            return 0;
        }
    }

    @Override
    public void draw(@NonNull Graphics2D graphics2D, @NonNull Size size) {
        final var length = size.getWidth()*widthFraction;
        final var margin = (size.getWidth()-length)/2;
        final Stroke backupStroke = graphics2D.getStroke();
        try {
            graphics2D.setStroke(stroke);
            graphics2D.setColor(color);
            graphics2D.drawLine(MathTool.roundedToInt(margin), 0, MathTool.roundedToInt(margin+length), 0);
        } finally {
            graphics2D.setStroke(backupStroke);
        }
    }
}
