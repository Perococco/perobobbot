package perobobbot.data.event;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnablePublisher;
import perobobbot.plugin.FunctionalPlugin;

@Configuration
@EnablePublisher
public class DataEventConfiguration {

    public static @NonNull FunctionalPlugin provider() {
        return FunctionalPlugin.with("Data Event", DataEventConfiguration.class);
    }

}
