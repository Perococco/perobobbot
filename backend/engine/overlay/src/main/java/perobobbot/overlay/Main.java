package perobobbot.overlay;

import lombok.NonNull;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OverlayController controller = OverlayController.create("Overlay", 1920, 1080, FrameRate.FPS_60);

        controller.start();
        controller.addClient(new O());

        new Scanner(System.in).nextLine();
    }

    public static final Color RED = new Color(246, 57, 57, 128);

    private static class O implements OverlayClient {

        private double x = 0;
        private double y = 0;
        private double dx = 220;
        private double dy = 150;

        private Font font;

        @Override
        public void initialize(@NonNull Overlay overlay) {
            this.x = overlay.getWidth() * 0.5;
            this.y = overlay.getHeight() * 0.5;
            final Font defaultFont = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment()
                                                                      .getAllFonts())
                                           .filter(f -> f.getFontName().equals("Arial"))
                                           .findFirst()
                                           .orElseThrow(() -> new RuntimeException("Could not find font"));
            this.font = Font.getFont("Fira Mono", defaultFont).deriveFont(144f);
        }

        @Override
        public void render(@NonNull OverlayIteration iteration) {
            final int width = iteration.getWidth();
            final int height = iteration.getHeight();
            final int sizey = Math.min(width, height) / 10;
            final int sizex = (int)(sizey*1.618);
            final int rgb = Color.HSBtoRGB((float) (iteration.getTime() % 1), 1.0f, 1.0f);
            final Color color = new Color((rgb & 0xFFFFFF) | (0x80000000), true);
            x = x + dx * iteration.getDeltaTime();
            y = y + dy * iteration.getDeltaTime();
            if (x < sizex) {
                x = 2 * sizex - x;
                dx = -dx;
            } else if (x > (width-sizex)) {
                x = 2 * (width-sizex) - x;
                dx = -dx;
            }
            if (y < sizey) {
                y = 2 * sizey - y;
                dy = -dy;
            } else if (y > (height-sizey)) {
                y = 2 * (height-sizey) - y;
                dy = -dy;
            }
            final Graphics2D g2 = (Graphics2D) iteration.getGraphics2D().create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            try {
                g2.translate(x, y);
                g2.setPaint(color);
                g2.fillRect(-sizex, -sizey, sizex*2, sizey*2);
                final TextLayout tx = new TextLayout("DVD", font, g2.getFontRenderContext());
                final Rectangle2D rect = tx.getBounds();
                g2.setPaint(Color.WHITE);
                tx.draw(g2, -(float)rect.getWidth()*0.5f, (float)rect.getHeight()*0.5f);
            } finally {
                g2.dispose();
            }
        }
    }

}
