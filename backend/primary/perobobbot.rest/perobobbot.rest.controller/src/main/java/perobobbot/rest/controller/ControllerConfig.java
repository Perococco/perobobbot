package perobobbot.rest.controller;

import lombok.NonNull;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

public class ControllerConfig {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.PRIMARY,"Rest Controllers",ControllerConfig.class);
    }
}
