package perobobbot.server.config.overlay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.overlay.Overlay;
import perobobbot.server.config.Service;

@Configuration
public class OverlayConfiguration {

    @Bean("default")
    @Service
    public Overlay defaultOverlay() {
        return OverlayComponent.create("Overlay");
    }

}
