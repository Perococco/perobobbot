package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class NoClientForPlatform extends DataException {

    @Getter
    private final @NonNull Platform platform;

    public NoClientForPlatform(@NonNull Platform platform) {
        super("Could not find a client for the platform '"+platform+"'");
        this.platform = platform;
    }
}
