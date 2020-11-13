package perobobbot.rendering.graphics2d.element;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.Size;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;


@Getter
public class TextElement implements BlockElement {

    private final @NonNull TextLayout textLayout;

    private final @NonNull Rectangle2D bounds;

    private final @NonNull Color color;

    private final @NonNull HAlignment alignment;

    public TextElement(@NonNull TextLayout textLayout, @NonNull Color color, @NonNull HAlignment alignment) {
        this.textLayout = textLayout;
        this.bounds = textLayout.getBounds();
        this.color = color;
        this.alignment = alignment;
    }

    @Override
    public double getRequiredWidth() {
        return bounds.getWidth();
    }

    @Override
    public double getHeight() {
        return textLayout.getAscent()+textLayout.getDescent()+textLayout.getLeading();
    }

    @Override
    public void draw(@NonNull Graphics2D graphics2D, @NonNull Size size) {

        final float margin = (float) (alignment.getPosition(size.getWidth()-bounds.getWidth()));

        graphics2D.setColor(color);
        textLayout.draw(graphics2D, margin, textLayout.getAscent());
    }
}
