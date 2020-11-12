package perobobbot.dvdlogo;

import lombok.NonNull;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

public class DVDLogoOverlay implements OverlayClient {

    private BufferedImage logo = null;

    private int logoWidth;
    private int logoHeight;

    private double vx, vy;
    private double x, y;

    private int widthMargin = 20;
    private int heightMargin = 40;

    private final Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
    private int idx = 0;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        try {
            logo = ImageIO.read(DVDLogoOverlay.class.getResourceAsStream("dvdlogo.png"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        this.logoWidth = logo.getWidth() + widthMargin * 2;
        this.logoHeight = logo.getHeight() + heightMargin * 2;
        this.x = 700;
        this.y = 70;
        this.vx = -200;
        this.vy = 200;
        this.idx = 0;
    }

    @Override
    public void dispose(@NonNull Overlay overlay) {
        logo = null;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final double dt = iteration.getDeltaTime();
        final var renderer = iteration.getRenderer();
        final var size = renderer.getOverlaySize();
        boolean bumped = false;

        x = x + iteration.getDeltaTime() * vx;
        y = y + iteration.getDeltaTime() * vy;
        if (x < 0) {
            x = -x;
            vx = -vx;
            bumped = true;
        } else if (x > size.getWidth() - logoWidth) {
            x = 2 * (size.getWidth() - logoWidth) - x;
            vx = -vx;
            bumped = true;
        }

        if (y < 0) {
            y = -y;
            vy = -vy;
            bumped = true;
        } else if (y > size.getHeight() - logoHeight) {
            y = 2 * (size.getHeight() - logoHeight) - y;
            vy = -vy;
            bumped = true;
        }

        if (bumped) {
            idx = (idx + 1) % (colors.length);
        }
        renderer.withPrivateContext(r -> {
            r.translate(x, y);
            r.setColor(colors[idx]);
            r.fillRect(0, 0, logoWidth, logoHeight);
            r.drawImage(logo, widthMargin, heightMargin);
        });
    }
}
