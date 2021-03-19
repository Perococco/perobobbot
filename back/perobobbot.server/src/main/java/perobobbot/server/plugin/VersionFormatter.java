package perobobbot.server.plugin;

import jplugman.api.Version;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VersionFormatter {

    public static @NonNull Version format(@NonNull String rawVersion) {
        return new VersionFormatter(rawVersion).format();
    }

    private final @NonNull String rawVersion;

    private int identifiantIndex;

    private String formattedVersion;

    private Version version;

    private @NonNull Version format() {
        this.findIdentifiantIndex();
        if (rawVersionAsAnIdentifiant()) {
            this.removeIdentifiant();
        } else {
            this.useRawVersionAsIs();
        }
        this.createVersion();
        return version;
    }

    private void findIdentifiantIndex() {
        this.identifiantIndex = rawVersion.indexOf("-");
    }

    private boolean rawVersionAsAnIdentifiant() {
        return this.identifiantIndex>=0;
    }


    private void removeIdentifiant() {
        this.formattedVersion = this.rawVersion.substring(0,this.identifiantIndex);
    }

    private void useRawVersionAsIs() {
        formattedVersion = rawVersion;
    }

    private void createVersion() {
        version = Version.with(formattedVersion);
    }
}
