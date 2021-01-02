package perobobbot.data.security;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.Packages;

@Configuration
public class DataSecurityConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Data Security", DataSecurityConfiguration.class);
    }

}
