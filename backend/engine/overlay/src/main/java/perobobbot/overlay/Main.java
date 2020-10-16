package perobobbot.overlay;

import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.sample.LogoDVD;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final SoundManager soundManager = SoundManager.create(48000);
        OverlayController controller = OverlayController.create("Overlay", 1920, 1080, FrameRate.FPS_60, soundManager);

        controller.start();
        controller.addClient(new LogoDVD());

        new Scanner(System.in).nextLine();
    }

}
