package perobobbot.frontfx.gui;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@RequiredArgsConstructor
public class PerobobbotProperties {

    @NonNull
    public static PerobobbotProperties create() throws IOException {
        final Properties properties = new Properties();
        properties.load(PerobobbotProperties.class.getResourceAsStream("perobobbot.properties"));
        return new PerobobbotProperties(
                properties.getProperty("application-name"),
                properties.getProperty("application-version")
                );
    }

    @NonNull
    @Getter
    private final String applicationName;

    @NonNull
    @Getter
    private final String applicationVersion;

    @NonNull
    public String getTitle() {
        return applicationName+" ("+applicationVersion+")";
    }
}
