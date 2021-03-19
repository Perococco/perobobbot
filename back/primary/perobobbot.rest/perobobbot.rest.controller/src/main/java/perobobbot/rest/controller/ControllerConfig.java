package perobobbot.rest.controller;

import lombok.NonNull;
import perobobbot.lang.Packages;

public class ControllerConfig {

    public static @NonNull Packages provider() {
        return Packages.with( "Rest Controllers", ControllerConfig.class);
    }
}
