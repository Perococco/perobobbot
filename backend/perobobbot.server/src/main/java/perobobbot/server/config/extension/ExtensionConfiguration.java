package perobobbot.server.config.extension;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.extension.AvailableExtensions;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class ExtensionConfiguration {

    private final @NonNull ApplicationContext applicationContext;

    @Bean
    public @NonNull AvailableExtensions availableExtensions() {
        final var extensions = ExtensionLister.gatherAllExtensions(applicationContext);
        return new AvailableExtensions(extensions);
    }


}
