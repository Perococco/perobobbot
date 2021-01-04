package perobobbot.data.event;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnablePublisher;
import perobobbot.lang.Packages;

@Configuration
@EnablePublisher
public class DataEventConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Data Event", DataEventConfiguration.class);
    }

}
