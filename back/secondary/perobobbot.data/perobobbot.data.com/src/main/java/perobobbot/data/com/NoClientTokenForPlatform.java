package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public class NoClientTokenForPlatform extends DataException {

    @Getter
    private final @NonNull Platform platform;

    public NoClientTokenForPlatform(@NonNull Platform platform) {
        super("Could not find a client token for the platform '"+platform+"'");
        this.platform = platform;
    }
}
