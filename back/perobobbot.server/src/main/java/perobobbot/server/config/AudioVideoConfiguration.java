package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlayController;
import perobobbot.rendering.Size;
import perobobbot.sound.SoundManager;
import perobobbot.sound.SoundResolver;

import java.nio.file.Path;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableScheduling
public class AudioVideoConfiguration {

    public static final int AUDIO_SAMPLING_RATE = 48_000;

    public static final Size OVERLAY_SIZE = Size._1600_900;
    public static final FrameRate OVERLAY_FRAME_RATE = FrameRate.FPS_30
            ;

    @Bean
    public SoundManager soundManager() {
        return SoundManager.create(AUDIO_SAMPLING_RATE);
    }

    @Bean(destroyMethod = "stop",initMethod = "start")
    public OverlayController overlayController(@NonNull SoundManager soundManager) {
        final var controller = OverlayController.create("Overlay",
                                                        OVERLAY_SIZE,
                                                        OVERLAY_FRAME_RATE,
                                                        soundManager);
        return controller;
    }

    @Bean
    public SoundResolver soundResolver(@Value("${perobot.sound.directory}") String soundDirectory) {
        return SoundResolver.soundFileResolver(Path.of(soundDirectory));
    }


}