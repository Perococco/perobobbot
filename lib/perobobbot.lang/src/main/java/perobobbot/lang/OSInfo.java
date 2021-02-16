package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Locale;

@Getter
public enum OSInfo {
    INSTANCE,
    ;

    private final @NonNull OS os;
    private final @NonNull String version;

    OSInfo() {
        {
            final String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
            this.os = Arrays.stream(OS.values())
                            .filter(o -> o.testOsName(osName))
                            .findFirst()
                            .orElse(OS.UNKNOWN);
            this.version = System.getProperty("os.version");
        }
    }

    public @NonNull String getAppConfigDirectory(@NonNull String applicationName) {
        return os.getAppConfigDirectory(applicationName).toAbsolutePath().toString();
    }

    public @NonNull String getConfigDirectory() {
        return os.getConfigDirectory().toAbsolutePath().toString();
    }
}
