package perobobbot.overlay;

import perobobbot.overlay.sample.LogoDVD;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OverlayController controller = OverlayController.create("Overlay", 1920, 1080, FrameRate.FPS_60);

        controller.start();
        controller.addClient(new LogoDVD());

        new Scanner(System.in).nextLine();
    }

}
