package perobobbot.rendering.graphics2d;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MathTool;
import perobobbot.lang.fp.Consumer1;
import perobobbot.rendering.*;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class RendererUsingGraphics2D implements Renderer {

    public static final int d2i(double value) {
        return MathTool.roundedToInt(value);
    }

    @Getter(AccessLevel.PACKAGE)
    private final @NonNull Graphics2D graphics2D;

    @Getter
    private final @NonNull ScreenSize drawingSize;

    @Override
    public void dispose() {
        graphics2D.dispose();
    }

    @Override
    public @NonNull Color getBackground() {
        return graphics2D.getColor();
    }

    @Override
    public @NonNull Renderer setBackground(@NonNull Color color) {
        graphics2D.setBackground(color);
        return this;
    }

    @Override
    public @NonNull Renderer setTextAntialiasing(boolean enable) {
        final var value = enable?RenderingHints.VALUE_TEXT_ANTIALIAS_ON:RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,value);
        return this;
    }

    @Override
    public @NonNull Renderer clearOverlay() {
        final Color backup = graphics2D.getBackground();
        graphics2D.setBackground(Renderer.TRANSPARENT);
        graphics2D.clearRect(0, 0, drawingSize.getWidth(), drawingSize.getHeight());
        graphics2D.setBackground(backup);
        return this;
    }

    @Override
    public @NonNull Renderer translate(double x, double y) {
        graphics2D.translate(x, y);
        return this;
    }

    @Override
    public @NonNull Renderer setColor(@NonNull Color color) {
        graphics2D.setColor(color);
        return this;
    }

    @Override
    public @NonNull Renderer setPaint(@NonNull Paint paint) {
        graphics2D.setPaint(paint);
        return this;
    }

    @Override
    public @NonNull Renderer fillCircle(double xc, double yc, double radius) {
        graphics2D.fillOval(d2i(xc - radius), d2i(yc - radius), d2i(radius * 2), d2i(radius * 2));
        return this;
    }

    @Override
    public @NonNull Renderer withPrivateContext(Consumer1<? super Renderer> drawer) {
        try (Renderer r = new RendererUsingGraphics2D((Graphics2D) graphics2D.create(), drawingSize)) {
            drawer.f(r);
        }
        return this;
    }

    @Override
    public @NonNull Renderer setStroke(@NonNull Stroke stoke) {
        graphics2D.setStroke(stoke);
        return this;
    }

    @Override
    public @NonNull Renderer withPrivateTransform(Consumer1<? super Renderer> drawer) {
        final AffineTransform backup = graphics2D.getTransform();
        try {
            drawer.f(this);
        } finally {
            graphics2D.setTransform(backup);
        }
        return this;
    }

    @Override
    public @NonNull Renderer drawLine(double x0, double y0, double x1, double y1) {
        graphics2D.drawLine(d2i(x0),d2i(y0),d2i(x1),d2i(y1));
        return this;
    }

    @Override
    public @NonNull Renderer drawImage(@NonNull BufferedImage image, double x, double y) {
        graphics2D.drawImage(image, d2i(x), d2i(y), null);
        return this;
    }

    @Override
    public @NonNull Renderer drawImage(@NonNull BufferedImage image, double x, double y, @NonNull Color bkgColor) {
        graphics2D.drawImage(image, d2i(x), d2i(y), bkgColor, null);
        return this;
    }

    @Override
    public @NonNull Renderer fillRect(double x, double y, double width, double height) {
        graphics2D.fillRect(d2i(x), d2i(y), d2i(width), d2i(height));
        return this;
    }

    @Override
    public @NonNull Font getFont() {
        return graphics2D.getFont();
    }

    @Override
    public @NonNull Renderer setFont(@NonNull Font font) {
        graphics2D.setFont(font);
        return this;
    }

    @Override
    public @NonNull Renderer setFontSize(float fontSize) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(fontSize));
        return this;
    }

    @Override
    public double getTextLineHeight() {
        return graphics2D.getFontMetrics().getHeight();
    }

    @Override
    public Rectangle2D getMaxCharBounds() {
        return graphics2D.getFontMetrics().getMaxCharBounds(graphics2D);
    }

    @Override
    public float getFontSize() {
        return graphics2D.getFont().getSize2D();
    }

    @Override
    public @NonNull Color getColor() {
        return graphics2D.getColor();
    }

    @Override
    public @NonNull BlockBuilder blockBuilder() {
        return new Graphics2DBlockBuilder(this.graphics2D);
    }

    @Override
    public void scale(double sx, double sy) {
        graphics2D.scale(sx, sy);
    }

    @Override
    public void rotate(double angle) {
        graphics2D.rotate(angle);
    }

    @Override
    public void rotate(double angle, double xc, double yc) {
        graphics2D.rotate(angle, xc, yc);
    }

    @Override
    public @NonNull Renderer drawString(
            @NonNull String string,
            double x,
            double y,
            @NonNull HAlignment hAlignment,
            @NonNull VAlignment vAlignment) {
        final Font font = graphics2D.getFont();
        final FontRenderContext frc = graphics2D.getFontRenderContext();
        final TextLayout textLayout = new TextLayout(string, font, frc);
        final Rectangle2D bounds = textLayout.getBounds();
        final var dx = hAlignment.getPosition(bounds.getWidth());
        final var dy = vAlignment.getPosition(textLayout.getAscent());

        textLayout.draw(graphics2D, (float) (x - dx), (float) (y - dy));
        return this;
    }
}
