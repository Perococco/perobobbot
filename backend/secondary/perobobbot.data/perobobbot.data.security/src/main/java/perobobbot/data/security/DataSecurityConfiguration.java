package perobobbot.data.security;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

@Configuration
public class DataSecurityConfiguration {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.SECONDARY,"Data Security", DataSecurityConfiguration.class);
    }

}
