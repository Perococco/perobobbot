package perobobbot.server.config.overlay;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.Overlay;
import perobobbot.overlay.OverlayController;
import perobobbot.server.config.AudioVideoConfig;
import perobobbot.server.config.Service;

@Configuration
@RequiredArgsConstructor
public class OverlayConfiguration {

    @NonNull
    private final SoundManager soundManager;

    @Bean("default")
    @Service
    public Overlay defaultOverlay() {
        return new OverlayComponent(overlayController());
    }

    @NonNull
    private OverlayController overlayController() {
        final var controller = OverlayController.create("Overlay",
                                                        AudioVideoConfig.OVERLAY_WIDTH,
                                                        AudioVideoConfig.OVERLAY_HEIGHT,
                                                        AudioVideoConfig.OVERLAY_FRAME_RATE,
                                                        soundManager);
        controller.start();
        return controller;
    }
}
