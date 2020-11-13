package perobobbot.rendering.graphics2d;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer1;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;
import perobobbot.rendering.VAlignment;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class RendererUsingGraphics2D implements Renderer {

    private final @NonNull Graphics2D graphics2D;

    @Getter
    private final @NonNull Size drawingSize;

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
    public @NonNull Renderer clearOverlay() {
        final Color backup = graphics2D.getBackground();
        graphics2D.setBackground(Renderer.TRANSPARENT);
        graphics2D.clearRect(0,0,drawingSize.getWidth(),drawingSize.getHeight());
        graphics2D.setBackground(backup);
        return this;
    }

    @Override
    public @NonNull Renderer translate(double x, double y) {
        graphics2D.translate(x,y);
        return this;
    }

    @Override
    public @NonNull Renderer setColor(@NonNull Color color) {
        graphics2D.setColor(color);
        return this;
    }

    @Override
    public @NonNull Renderer fillCircle(int xc, int yc, int radius) {
        graphics2D.fillOval(xc-radius,yc-radius,radius*2,radius*2);
        return this;
    }

    @Override
    public @NonNull Renderer withPrivateContext(Consumer1<? super Renderer> drawer) {
        try (Renderer r = new RendererUsingGraphics2D((Graphics2D)graphics2D.create(), drawingSize)) {
            drawer.f(r);
        }
        return this;
    }

    @Override
    public @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y) {
        graphics2D.drawImage(image,x,y,null);
        return this;
    }

    @Override
    public @NonNull Renderer drawImage(@NonNull BufferedImage image, int x, int y, @NonNull Color bkgColor) {
        graphics2D.drawImage(image,x,y,bkgColor,null);
        return this;
    }

    @Override
    public @NonNull Renderer fillRect(int x, int y, int width, int height) {
        graphics2D.fillRect(x,y,width,height);
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
    public @NonNull Renderer drawString(
            @NonNull String string,
            @NonNull double x,
            @NonNull double y,
            @NonNull HAlignment hAlignment,
            @NonNull VAlignment vAlignment) {
        final Font font = graphics2D.getFont();
        final FontRenderContext frc = graphics2D.getFontRenderContext();
        final TextLayout textLayout = new TextLayout(string,font,frc);
        final Rectangle2D bounds = textLayout.getBounds();
        final var dx = hAlignment.getPosition(bounds.getWidth());
        final var dy = vAlignment.getPosition(textLayout.getAscent());

        textLayout.draw(graphics2D,(float)(x-dx),(float)(y-dy));
        return this;
    }
}
