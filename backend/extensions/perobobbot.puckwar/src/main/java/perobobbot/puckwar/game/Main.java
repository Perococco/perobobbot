package perobobbot.puckwar.game;

import perobobbot.overlay.api.ImageTester;

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
        return Target.create(Math.min(with, height));
    }
}
