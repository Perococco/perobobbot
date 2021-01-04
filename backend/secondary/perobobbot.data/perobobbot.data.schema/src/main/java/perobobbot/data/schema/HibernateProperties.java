package perobobbot.data.schema;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class HibernateProperties {

    public static final String DEFAULT_HIBERNATE_CONFIGURATION_PROPERTY_FILE = "/export-hibernate-configuration.properties";

    @NonNull
    public static Properties get() {
        return Holder.PROPERTIES;
    }

    private static class Holder {

        private static final Properties PROPERTIES;

        static {
            final Properties properties = new Properties();
            try (InputStream is = HibernateProperties.class.getResource(DEFAULT_HIBERNATE_CONFIGURATION_PROPERTY_FILE).openStream()) {
                properties.load(is);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            PROPERTIES = properties;
        }
    }
}
