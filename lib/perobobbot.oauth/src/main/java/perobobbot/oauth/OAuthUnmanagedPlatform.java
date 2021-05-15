package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;

public class OAuthUnmanagedPlatform extends PerobobbotException {

    @Getter
    private final @NonNull Platform platform;


    public OAuthUnmanagedPlatform(@NonNull Platform platform) {
        super("The Platform '"+platform+"' does not have an OAuthManager available");
        this.platform = platform;
    }
}
