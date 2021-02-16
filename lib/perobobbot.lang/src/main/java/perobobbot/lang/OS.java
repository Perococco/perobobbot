package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public enum OS {
    LINUX(ImmutableList.of("linux", "mpe/ix", "freebsd", "irix", "digital unix", "unix")),
    POSIX_UNIX(ImmutableList.of("sun os","sunos","solaris","aix","hp-ux")),
    WINDOWS(ImmutableList.of("windows")),
    MACOS(ImmutableList.of("mac os")),
    UNKNOWN(ImmutableList.of()),
    ;

    private final @NonNull ImmutableList<String> osNameSubStrings;

    OS(String... osNameSubStrings) {
        this.osNameSubStrings = ImmutableList.copyOf(osNameSubStrings);
    }

    public boolean testOsName(@NonNull String osName) {
        return osNameSubStrings.stream().anyMatch(osName::contains);
    }

    public @NonNull Path getAppConfigDirectory(@NonNull String name) {
        return getConfigDirectory().resolve(name);
    }

    public @NonNull Path getConfigDirectory() {
        final Path home = Path.of(System.getProperty("user.home"));
        if (this == WINDOWS) {
            return home.resolve("AppData").resolve("Roaming");
        } else {
            return home.resolve(".config");
        }

    }
}
