package perobobbot.rendering.graphics2d;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.rendering.Block;
import perobobbot.rendering.BlockBuilder;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.graphics2d.element.BlockElement;
import perobobbot.rendering.graphics2d.element.SeparatorElement;
import perobobbot.rendering.graphics2d.element.TextElement;

import java.awt.*;
import java.awt.font.TextLayout;

public class Graphics2DBlockBuilder implements BlockBuilder {

    private final ImmutableList.Builder<BlockElement> elements = ImmutableList.builder();

    private final @NonNull Graphics2D graphics2D;

    private BackgroundInfo backgroundInfo = BackgroundInfo.withoutColor(0);

    public Graphics2DBlockBuilder(@NonNull Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public @NonNull BlockBuilder addString(@NonNull String text, @NonNull HAlignment alignment) {
        final var textLayout = new TextLayout(text,graphics2D.getFont(),graphics2D.getFontRenderContext());
        elements.add(new TextElement(textLayout, graphics2D.getColor(), alignment));
        return this;
    }

    @Override
    public @NonNull BlockBuilder addSeparator() {
        elements.add(new SeparatorElement(1, graphics2D.getStroke(),graphics2D.getColor()));
        return this;
    }

    @Override
    public @NonNull BlockBuilder setBackgroundColor(@NonNull Color color) {
        this.backgroundInfo = this.backgroundInfo.withColor(color);
        return this;
    }

    @Override
    public @NonNull BlockBuilder setBackgroundMargin(int margin) {
        this.backgroundInfo = this.backgroundInfo.withMargin(margin);
        return this;
    }

    public @NonNull BlockBuilder setFontSize(float fontSize) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(fontSize));
        return this;
    }

    @Override
    public @NonNull BlockBuilder setFont(@NonNull Font font) {
        graphics2D.setFont(font);
        return this;
    }

    @Override
    public @NonNull BlockBuilder setColor(@NonNull Color color) {
        graphics2D.setColor(color);
        return this;
    }

    @Override
    public @NonNull Block build() {
        return Graphics2DBlock.create(graphics2D,backgroundInfo,elements.build());
    }
}
