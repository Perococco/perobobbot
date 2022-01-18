package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class NoBotTokenFound extends DataException{

    @Getter
    private final @NonNull Platform platform;

    public NoBotTokenFound(@NonNull Platform platform) {
        super("No bot token defined for platform '"+platform+"'");
        this.platform = platform;
    }
}
