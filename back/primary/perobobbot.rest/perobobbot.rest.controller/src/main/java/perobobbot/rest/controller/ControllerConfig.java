package perobobbot.rest.controller;

import lombok.NonNull;
import perobobbot.plugin.FunctionalPlugin;

public class ControllerConfig {

    public static @NonNull FunctionalPlugin provider() {
        return FunctionalPlugin.with( "Rest Controllers", ControllerConfig.class);
    }
}
