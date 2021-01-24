package perobobbot.data.event;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnablePublisher;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

@Configuration
@EnablePublisher
public class DataEventConfiguration {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.SECONDARY,"Data Event", DataEventConfiguration.class);
    }

}
