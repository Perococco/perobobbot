package perobobbot.data.security;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import perobobbot.plugin.FunctionalPlugin;
import perobobbot.plugin.Plugin;
import perobobbot.lang.PluginType;

@Configuration
public class DataSecurityConfiguration {

    public static @NonNull FunctionalPlugin provider() {
        return FunctionalPlugin.with("Data Security", DataSecurityConfiguration.class);
    }

}
