package perobobbot.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.sound.SoundManager;

@Configuration
public class SoundConfiguration {

    @Bean
    public SoundManager soundManager() {
        return SoundManager.create(AudioVideoConfig.AUDIO_SAMPLING_RATE);
    }
}
