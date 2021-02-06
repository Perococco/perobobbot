package perobobbot.rendering.graphics2d;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MathTool;
import perobobbot.rendering.Block;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.Size;
import perobobbot.rendering.VAlignment;
import perobobbot.rendering.graphics2d.element.BlockElement;

import java.awt.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Graphics2DBlock implements Block {

    public static int d2i(double value) {
        return MathTool.roundedToInt(value);
    }

    public static @NonNull Block create(@NonNull Graphics2D graphics2D,
                                        @NonNull BackgroundInfo backgroundInfo,
                                        @NonNull ImmutableList<BlockElement> elements) {
        final var maxWidth = elements.stream()
                                     .mapToDouble(BlockElement::getRequiredWidth)
                                     .max()
                                     .orElse(0);

        final var totalHeight = elements.stream()
                                        .mapToDouble(BlockElement::getHeight)
                                        .sum();

        if (maxWidth <= 0 || totalHeight <= 0) {
            return Block.EMPTY;
        }
        final var backgroundMargin = backgroundInfo.getMargin();
        final var size = Size.with(maxWidth + backgroundMargin * 2, totalHeight + backgroundMargin * 2);
        return new Graphics2DBlock(graphics2D, backgroundInfo, elements, size);
    }

    private final @NonNull Graphics2D graphics2D;
    private final @NonNull BackgroundInfo backgroundInfo;
    private final @NonNull ImmutableList<BlockElement> elements;

    @Getter
    private final @NonNull Size size;

    @Override
    public void draw(double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment) {
        final var ox = hAlignment.getPosition(size.getWidth());
        final var oy = vAlignment.getPosition(size.getHeight());
        graphics2D.translate(x - ox, y - oy - size.getHeight());
        backgroundInfo.getColor().ifPresent(this::drawBackground);
        this.drawBlockContent();
    }


    private void drawBackground(@NonNull Color backgroundColor) {
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, size.getWidthAsInt(), size.getHeightAsInt());
    }

    private void drawBlockContent() {
        final var margin = backgroundInfo.getMargin();
        final var contentSize = size.addMargin(-margin * 2);
        graphics2D.translate(margin, margin);
        for (BlockElement element : elements) {
            element.draw(graphics2D, contentSize);
            graphics2D.translate(0, element.getHeight());
        }
    }

}
