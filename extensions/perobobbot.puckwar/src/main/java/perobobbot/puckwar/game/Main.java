package perobobbot.puckwar.game;

import perobobbot.rendering.ImageTester;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main extends ImageTester {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().launch();
        });
    }

    @Override
    protected BufferedImage getImage(int with, int height) {
        final int size = Math.min(with, height);
        return StartPointImage.create(size);
    }
}
