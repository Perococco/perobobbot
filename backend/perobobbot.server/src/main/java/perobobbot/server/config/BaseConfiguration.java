package perobobbot.server.config;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import perobobbot.common.sound.SoundManager;
import perobobbot.services.Services;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableScheduling
public class BaseConfiguration {

    @NonNull
    private final ApplicationContext applicationContext;

    @Bean
    public SoundManager soundManager() {
        return SoundManager.create(AudioVideoConfig.AUDIO_SAMPLING_RATE);
    }

    @Bean
    public Services services() {
        final Map<String,Object> services = applicationContext.getBeansWithAnnotation(Service.class);

        if (LOG.isInfoEnabled()) {
            services.forEach((n,b) -> LOG.info("Add service '{}' of type '{}'",n,b.getClass()));
        }

        return Services.create(ImmutableSet.copyOf(services.values()));
    }

}
