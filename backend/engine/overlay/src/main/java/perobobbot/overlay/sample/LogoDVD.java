package perobobbot.overlay.sample;

import lombok.NonNull;
import perobobbot.overlay.Overlay;
import perobobbot.overlay.OverlayClient;
import perobobbot.overlay.OverlayIteration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.UUID;

public class LogoDVD implements OverlayClient {

    private static final Path AUDIO_FILE = Path.of("/home/perococco/Documents/foghorn-daniel_simon.wav");


    private BufferedImage logo = null;

    private int logoWidth;
    private int logoHeight;

    private double vx, vy;
    private double x, y;

    private int widthMargin = 20;
    private int heightMargin = 40;

    private final Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
    private int idx = 0;

    private UUID soundId = null;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        try {
            logo = ImageIO.read(LogoDVD.class.getResourceAsStream("dvdlogo.png"));
            this.soundId = overlay.registerSoundResource(AUDIO_FILE.toUri().toURL());
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
        overlay.unregisterSoundResource(soundId);
        soundId = null;
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {

        boolean bumped = false;

        x = x + iteration.getDeltaTime() * vx;
        y = y + iteration.getDeltaTime() * vy;
        if (x < 0) {
            x = -x;
            vx = -vx;
            bumped = true;
        } else if (x > iteration.getWidth() - logoWidth) {
            x = 2 * (iteration.getWidth() - logoWidth) - x;
            vx = -vx;
            bumped = true;
        }

        if (y < 0) {
            y = -y;
            vy = -vy;
            bumped = true;
        } else if (y > iteration.getHeight() - logoHeight) {
            y = 2 * (iteration.getHeight() - logoHeight) - y;
            vy = -vy;
            bumped = true;
        }

        if (bumped) {
            idx = (idx + 1) % (colors.length);
            iteration.playSound(soundId);
        }
        final Graphics2D g2 = iteration.createGraphics2D();
        try {
            g2.translate(x, y);
            g2.setPaint(colors[idx]);
            g2.fillRect(0, 0, logoWidth, logoHeight);

            g2.drawImage(logo, widthMargin, heightMargin, null);
        } finally {
            g2.dispose();
        }
    }
}
