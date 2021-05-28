package perobobbot.rest.controller;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Packages;
import perobobbot.oauth.OAuthModule;

@Configuration
public class ControllerConfig {

    public static @NonNull Packages provider() {
        return Packages.with( "Rest Controllers", ControllerConfig.class);
    }

}
