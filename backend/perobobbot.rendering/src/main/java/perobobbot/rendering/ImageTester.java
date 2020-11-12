package perobobbot.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageTester {

    public void launch() {
        final JPanel container = new JPanel(new BorderLayout());
        container.add(createPanelView(),BorderLayout.CENTER);

        final JFrame frame = new JFrame("Image Viewer");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(container);
        frame.pack();
        frame.setVisible(true);

    }

    private JPanel createPanelView() {
        final JPanel panel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final var insets = this.getInsets();
                final var x = insets.left;
                final var y = insets.top;
                final var width = getWidth() - insets.left - insets.right;
                final var height = getHeight() - insets.top - insets.bottom;

                final BufferedImage image = getImage(width, height);

                final var imageWidth = image.getWidth();
                final var imageHeight = image.getHeight();

                //r*imageWidth<=width et r*imageHeight<=height r maximal
                final double ratio = Math.min(1,Math.min((height+0.0)/imageHeight, (0.0+width)/imageWidth));

                final var w = (int)Math.round(ratio*imageWidth);
                final var h = (int)Math.round(ratio*imageHeight);

                g.drawImage(image, x+(width-w)/2, y+(height-h)/2, w,h, null);
            }
        };
        panel.setBackground(Color.MAGENTA);
        return panel;
    }

    protected abstract BufferedImage getImage(int with, int height);
}
