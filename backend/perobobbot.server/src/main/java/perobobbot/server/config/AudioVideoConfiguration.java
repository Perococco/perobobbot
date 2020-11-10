package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayController;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableScheduling
public class AudioVideoConfiguration {

    public static final int AUDIO_SAMPLING_RATE = 48_000;

    public static final int OVERLAY_WIDTH = 1920;

    public static final int OVERLAY_HEIGHT = 1080;

    public static final FrameRate OVERLAY_FRAME_RATE = FrameRate.FPS_30
            ;

    @Bean
    public SoundManager soundManager() {
        return SoundManager.create(AUDIO_SAMPLING_RATE);
    }

    @Bean(destroyMethod = "stop",initMethod = "start")
    public OverlayController overlayController(@NonNull SoundManager soundManager) {
        final var controller = OverlayController.create("Overlay",
                                                        OVERLAY_WIDTH,
                                                        OVERLAY_HEIGHT,
                                                        OVERLAY_FRAME_RATE,
                                                        soundManager);
        return controller;
    }


}
