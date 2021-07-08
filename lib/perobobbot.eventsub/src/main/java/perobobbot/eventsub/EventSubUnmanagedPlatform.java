package perobobbot.eventsub;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;

public class EventSubUnmanagedPlatform extends PerobobbotException {

    @Getter
    private final @NonNull Platform platform;

    public EventSubUnmanagedPlatform(@NonNull Platform platform) {
        super("The platform '"+platform+"' does not have a manager for event sub");
        this.platform = platform;
    }
}
